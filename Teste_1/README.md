Para testar e promover alterações no cluster Hadoop com base na configuração do docker-compose.yml, você pode seguir um plano estruturado. Vamos detalhar os passos e as mudanças que você pode implementar para avaliar o impacto das alterações no Hadoop.

## Passos para Realizar o Teste

### 1. Entender a Configuração Atual
Certifique-se de entender como o Hadoop está configurado atualmente, incluindo a configuração do YARN e do HDFS. Isso inclui verificar os arquivos de configuração (config.env) e como as variáveis são aplicadas.

### 2. Definir as Alterações
Escolha pelo menos 5 alterações a serem feitas. Essas mudanças podem ser aplicadas nos seguintes aspectos do Hadoop:

* HDFS: Alterar a configuração do HDFS, como o número de réplicas ou a diretoria de armazenamento.

* YARN: Alterar configurações relacionadas ao ResourceManager e NodeManager, como alocação de memória e CPUs.

* Scheduler: Modificar as configurações do Capacity Scheduler para ajustar o modo como os recursos são distribuídos entre as filas e usuários.

### 3. Modificar os Arquivos de Configuração
Faça as seguintes alterações nos arquivos de configuração do Hadoop e ajuste o config.env conforme necessário:

CORE-SITE.XML: Modificar o fs.defaultFS para apontar para um novo Namenode ou ajustar o fs.ha.namenodes para testar a alta disponibilidade.
```
CORE-SITE.XML_fs.defaultFS=hdfs://namenode
CORE-SITE.XML_fs.ha.namenodes.ns1=namenode,namenode2
```

HDFS-SITE.XML: Alterar o número de réplicas ou diretório de armazenamento.
```
HDFS-SITE.XML_dfs.replication=2
HDFS-SITE.XML_dfs.datanode.data.dir=file:///data
```

YARN-SITE.XML: Ajustar a alocação de memória e CPU para aplicações.
```
YARN-SITE.XML_yarn.nodemanager.resource.memory-mb=4096
YARN-SITE.XML_yarn.scheduler.maximum-allocation-mb=2048
YARN-SITE.XML_yarn.scheduler.minimum-allocation-mb=1024
```

CAPACITY-SCHEDULER.XML: Modificar a capacidade das filas e a alocação de recursos.
```
CAPACITY-SCHEDULER.XML_yarn.scheduler.capacity.root.queues=default,high
CAPACITY-SCHEDULER.XML_yarn.scheduler.capacity.root.default.capacity=80
CAPACITY-SCHEDULER.XML_yarn.scheduler.capacity.root.high.capacity=20
```

### 4 MapReduce

Para gerar o Mapreduce digite na pasta "REduce":

```
$ jar cf mapreduce-test.jar MapReduceTest.class MapperClass.class ReducerClass.class
```

#### 4.1 Como rodar

depois de levantar o container como:

$ docker-compose up

rodar:

Copie o Arquivo .jar para o Container do Namenode:

$ docker cp /Files/wordcount.jar namenode:/user/hadoop/

Colocar o Arquivo .jar no HDFS

$ docker exec -it namenode hdfs dfs -put /user/hadoop/wordcount.jar /user/hadoop/

Carregar Dados no HDFS

$ docker exec -it namenode hdfs dfs -put /Files/file.txt /user/hadoop/input/

Verificar Arquivos no HDFS:

$ docker exec -it namenode hdfs dfs -ls /user/hadoop/input/

### Executar o Job MapReduce

$ docker exec -it resourcemanager yarn jar /user/hadoop/wordcount.jar <main-class> /user/hadoop/input/ /user/hadoop/output/


## Verificar o Status do Job
Para verificar o status do job, você pode acessar a interface web do ResourceManager:

ResourceManager Web UI: http://localhost:8088

Haddop: http://localhost:9870/

## Fontes:

https://phoenixnap.com/kb/install-hadoop-ubuntu

https://medium.com/@guillermovc/setting-up-hadoop-with-docker-and-using-mapreduce-framework-c1cd125d4f7b

https://www.apache.org/dyn/closer.cgi/hadoop/common/hadoop-3.4.0/hadoop-3.4.0-aarch64.tar.gz