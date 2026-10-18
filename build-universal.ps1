param(
    [string]$Project = "C:\Users\LorenPC\Documents\DinoMod",
    [string]$McVersion = "26.3"
)
$ErrorActionPreference = "Stop"

Write-Host "=== universal jar (MC $McVersion stack, wide manifest range) ==="
$arg = "-Pmc_version=" + $McVersion
& (Join-Path $Project "gradlew.bat") :fabric:jar :neoforge:jar mergeJars "$arg" --console=plain
if ($LASTEXITCODE -ne 0) { throw "build failed" }

Get-ChildItem (Join-Path $Project "build\forgix") -Filter *.jar | Select-Object Name, Length
