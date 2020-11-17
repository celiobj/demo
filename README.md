# Teste técnico
> Aplicação de atualização em lotes com Spring Batch.


A aplicação roda seguindo os passos abaixo, foi implementado o conceito de spring batch onde foram criados o JOB e o STEP dividindo esse step em reader(leitura do arquivo csv), processor(processamento do arquivo para ficar de acordo com o exigido pelo serviço) e writer(escrita do registro no arquivo de retorno).


## Installation

Windows:

No terminal de comando:
clonar o projeto uma pasta preferida, adotaremos a pasta "demo"
cd demo
Na pasta File, estarão os arquivos:
conta.csv
local.csv
Na pasta Logs, estará o log da aplicação.

## Usage example

No arquivo de log irá listando todos os resultados das exportações

## Development setup

Entrar na pasta "target"
cd target
executar o comando
java -jar SincronizacaoReceita-0.0.1-SNAPSHOT.jar
