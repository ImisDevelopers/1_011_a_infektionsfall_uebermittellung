#!/bin/bash

# Will take ages for the first run...
helm upgrade --install --atomic sentry stable/sentry \
  --set ingress.enabled=true,ingress.hostname=sentry.imis-prototyp.de,service.type=ClusterIP \
  --set ingress.annotations."kubernetes.io\/ingress.class"=traefik

helm upgrade --install --atomic prometheus stable/prometheus \
  --set server.global.scrape_interval=30s

helm upgrade --install --atomic grafana stable/grafana \
  --set "ingress.enabled=true,ingress.hosts={grafana.imis-prototyp.de}" \
  --set "persistence.enabled=true"