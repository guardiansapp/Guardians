# Guardians

> Aplicativo de ajuda por meio de notificações em um rede solidária de proteção.

## Como instalar?

### Backend

> **Pré-requisitos:**
Java 11 e Maven instalado e configurado na sua maquina.

> Faça o clone do repositório com o seguinte comando:

~~~console
git clone https://github.com/guardiansapp/Guardians.git
~~~

> Logo em seguida entre na pasta do repositório:

~~~console
cd Guardians/Backend
~~~

> Faça o build do projeto com o maven. Esse processo pode demorar um pouco.

~~~console
mvn package
~~~

> Pronto, seu arquivo jar foi gerado. Ele foi criado na pasta 'target', entre na pasta e execute o jar

~~~console
cd target
java -jar Backend-1.0.jar
~~~

> E pronto o backend da aplicação Guardians está em execução na porta 8080.

> Por exemplo: você irá conseguir acessar os endpoint utilizando o caminho http://localhost:8080/

### Frontend

> **Pré-requisitos:**
npm instalado e configurado corretamente.

> Se ainda estiver na pasta Backend volte um diretório e entre na pasta Frontend

~~~console
cd Frontend
~~~

> Instale o expo utilizando o comando 
~~~console
npm install -g expo-cli
~~~

> Baixe as dependencias do projeto com o comando:

~~~console
npm install
~~~

> Execute o aplicativo com o comando 

~~~console
npm start
~~~

> O aplicativo irá ser executado com um banco de dados de teste, sempre que fechar o aplicativo os dados serão perdidos, para persistir os dados crie uma conexão com um banco postgreSQL mudando o profile de execução no arquivo de propriedades presente na pasta resources.

> No seu browser irá abrir uma janela com um QRcode, para vizualizar o aplicativo escaneie o QRcode com o aplicativo 'Expo Go' que pode ser localizado na google play.

> Para mais informações ou sugestões de melhoria acesse nosso site: https://guardiansapp.github.io.
