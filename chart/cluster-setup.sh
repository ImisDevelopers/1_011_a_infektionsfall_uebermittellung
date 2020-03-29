#!/bin/bash

helm upgrade --install sentry stable/sentry \
  --set ingress.enabled=true,ingress.hostname=sentry.imis-prototyp.de,service.type=ClusterIP \
  --set ingress.annotations."kubernetes.io\/ingress.class"=traefik