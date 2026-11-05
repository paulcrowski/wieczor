#!/usr/bin/env python3
"""Kopiuje app/src/main/assets/index.html do docs/ (wersja na GitHub Pages).

Dopisuje politykę CSP (strona może łączyć się tylko ze sobą) i wersjonuje
cache service workera po skrócie SHA-256 pliku, żeby aktualizacje trafiały do ludzi.
"""
import hashlib, pathlib

root = pathlib.Path(__file__).resolve().parent.parent
src = (root / "app/src/main/assets/index.html").read_text(encoding="utf-8")

csp = ("default-src 'none'; script-src 'unsafe-inline'; style-src 'unsafe-inline'; "
       "img-src 'self' data:; manifest-src 'self'; connect-src 'self'; worker-src 'self'; "
       "form-action 'none'; base-uri 'none'")
marker = '<meta charset="utf-8">'
assert src.count(marker) == 1
html = src.replace(marker, marker + f'\n<meta http-equiv="Content-Security-Policy" content="{csp}">')
(root / "docs/index.html").write_text(html, encoding="utf-8")

build = hashlib.sha256(html.encode("utf-8")).hexdigest()[:10]
sw = (root / "tools/sw.template.js").read_text(encoding="utf-8").replace("__BUILD__", build)
(root / "docs/sw.js").write_text(sw, encoding="utf-8")
print("docs/index.html + docs/sw.js, build", build)
