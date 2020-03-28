{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "imis.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "imis.client.fullname" -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- printf "%s-client" .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s-client" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{/* server */}}
{{- define "imis.server.fullname" -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- printf "%s-server" .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s-server" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "imis.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}


{{/*
Common labels
*/}}
{{- define "imis.labels" -}}
helm.sh/chart: {{ include "imis.chart" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
app.kubernetes.io/git-sha: {{ .Values.gitSha }}
app.kubernetes.io/environment: {{ .Values.env }}
{{- end -}}

{{/*
Common client labels
*/}}
{{- define "imis.client.labels" -}}
{{ include "imis.labels" . }}
{{ include "imis.client.selectorLabels" . }}
{{- end -}}

{{/*
Common server labels
*/}}
{{- define "imis.server.labels" -}}
{{ include "imis.labels" . }}
{{ include "imis.server.selectorLabels" . }}
{{- end -}}

{{/*
client Selector labels
*/}}
{{- define "imis.client.selectorLabels" -}}
app.kubernetes.io/name: {{ include "imis.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end -}}{{/*
server Selector labels
*/}}
{{- define "imis.server.selectorLabels" -}}
app.kubernetes.io/name: {{ include "imis.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end -}}

{{/*
Create the name of the service account to use
*/}}
{{- define "imis.certs.client.certName" -}}
{{- printf "%s-cert" .Values.ingress.client.domain  | trunc 63 | replace "." "-" | trimSuffix "-" -}}
{{- end -}}

{{- define "imis.certs.server.certName" -}}
{{- printf "%s-cert" .Values.ingress.server.domain  | trunc 63 | replace "." "-" | trimSuffix "-" -}}
{{- end -}}
