#!/usr/bin/env python3
"""Compara mensagens JSON recebidas com registros normalizados exportados do InfluxDB."""
import argparse
import json
import math
import re
import sys
from collections import Counter
from pathlib import Path

SCHEMAS = {
    "energia": (("tensao", "corrente", "potencia", "energiaKwh", "frequencia", "fatorPotencia", "esp32Mac"),
                ("tensao", "corrente", "potencia", "energiaKwh", "frequencia", "fatorPotencia"),
                ("esp32Mac",)),
    "wifi": (("cliente", "bssid", "rssi", "canal", "tamanho"),
             ("rssi", "canal", "tamanho"), ("cliente", "bssid")),
    "ambiente": (("valor", "campo", "sensor", "esp32Mac"), ("valor",), ("esp32Mac",)),
}
MAC = re.compile(r"^(?:[0-9A-Fa-f]{2}:){5}[0-9A-Fa-f]{2}$")


def read_jsonl(path, schema):
    required, numeric, macs = schema
    records, errors = [], []
    with path.open(encoding="utf-8", errors="replace") as stream:
        for line_no, line in enumerate(stream, 1):
            start = line.find("{")
            if start < 0:
                continue
            try:
                obj, _ = json.JSONDecoder().raw_decode(line[start:])
            except json.JSONDecodeError:
                errors.append({"linha": line_no, "erro": "json_invalido"})
                continue
            if not isinstance(obj, dict):
                errors.append({"linha": line_no, "erro": "nao_objeto"})
                continue
            missing = [key for key in required if key not in obj]
            if missing:
                errors.append({"linha": line_no, "erro": "campos_ausentes", "campos": missing})
                continue
            invalid = None
            for key in numeric:
                value = obj[key]
                if isinstance(value, bool) or not isinstance(value, (int, float)) or not math.isfinite(float(value)):
                    invalid = "campo_numerico_invalido:" + key
                    break
            if invalid is None:
                for key in macs:
                    value = obj[key]
                    if not isinstance(value, str) or not MAC.fullmatch(value):
                        invalid = "mac_invalido:" + key
                        break
            if invalid:
                errors.append({"linha": line_no, "erro": invalid})
            else:
                records.append(obj)
    return records, errors


def norm(obj, schema):
    required, numeric, macs = schema
    return tuple((key, round(float(obj[key]), 6) if key in numeric else
                  obj[key].upper() if key in macs else obj[key]) for key in required)


def main():
    ap = argparse.ArgumentParser(description=__doc__)
    ap.add_argument("--tipo", choices=sorted(SCHEMAS), required=False)
    ap.add_argument("--entrada", type=Path)
    ap.add_argument("--persistidos", type=Path)
    ap.add_argument("--saida", type=Path)
    ap.add_argument("--autoteste", action="store_true")
    args = ap.parse_args()
    if args.autoteste:
        for typ, schema in SCHEMAS.items():
            required, numeric, macs = schema
            sample = {key: (12.5 if key in numeric else "AA:BB:CC:DD:EE:FF" if key in macs else "teste") for key in required}
            assert len(norm(sample, schema)) == len(required)
        print("AUTOTESTE OK: esquemas de comparação carregados.")
        print("Este autoteste não verifica conexão com os bancos nem a aplicação Java.")
        return 0
    if not args.tipo or not args.entrada or not args.persistidos:
        ap.error("informe --tipo, --entrada e --persistidos, ou use --autoteste")
    if not args.entrada.is_file() or not args.persistidos.is_file():
        ap.error("um dos arquivos informados não existe")
    schema = SCHEMAS[args.tipo]
    source, err_source = read_jsonl(args.entrada, schema)
    target, err_target = read_jsonl(args.persistidos, schema)
    counts_source = Counter(norm(x, schema) for x in source)
    counts_target = Counter(norm(x, schema) for x in target)
    matched = sum((counts_source & counts_target).values())
    missing = sum((counts_source - counts_target).values())
    extras = sum((counts_target - counts_source).values())
    total = len(source)
    result = {
        "tipo": args.tipo,
        "mensagens_entrada_validas": total,
        "registros_persistidos_validos": len(target),
        "correspondencias": matched,
        "mensagens_nao_encontradas": missing,
        "registros_extras_ou_duplicados": extras,
        "erros_entrada": err_source,
        "erros_persistidos": err_target,
        "taxa_persistencia_percentual": round(100 * len(target) / total, 2) if total else 0.0,
        "taxa_integridade_percentual": round(100 * matched / total, 2) if total else 0.0,
        "nota": "Resultados válidos somente se a exportação representar o mesmo intervalo e os mesmos campos da entrada."
    }
    output = json.dumps(result, ensure_ascii=False, indent=2)
    print(output)
    if args.saida:
        args.saida.write_text(output + "\n", encoding="utf-8")
    return 1 if not total or missing or extras or err_source or err_target else 0


if __name__ == "__main__":
    sys.exit(main())
