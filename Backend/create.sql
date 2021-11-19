create sequence grupos_sequence start 1 increment 50
create sequence notificacoes_sequence start 1 increment 50
create sequence usuarios_sequence start 1 increment 50
create table acessos (usuario_id int8 not null, dica1 varchar(255), dica2 varchar(255), nome_usuario varchar(255) not null, senha varchar(255) not null, primary key (usuario_id))
create table configuracoes (usuario_id int8 not null, compartilhar_localizacao boolean not null, endereco_visivel boolean not null, enviar_para_grupos boolean not null, idade_visivel boolean not null, pre_mensagem varchar(255) not null, telefone_visivel boolean not null, primary key (usuario_id))
create table enderecos (usuario_id int8 not null, cep varchar(255) not null, bairro varchar(255) not null, cidade varchar(255) not null, complemento varchar(255), estado varchar(255) not null, logradouro varchar(255) not null, numero varchar(255) not null, primary key (usuario_id))
create table grupos (id int8 not null, nome varchar(255) not null, primary key (id))
create table grupos_permitidos (grupo_id int8 not null, usuario_id int8 not null, primary key (usuario_id, grupo_id))
create table notificacoes (id int8 not null, latitude float8, longitude float8, mensagem varchar(255), timestamp timestamp, notificacao_guardiao_notificacao_id int8, usuario_id int8, primary key (id))
create table notificacoes_grupos (recebida boolean, usuario_id int8 not null, grupo_id int8 not null, notificacao_id int8 not null, primary key (grupo_id, notificacao_id, usuario_id))
create table notificacoes_guardiao (recebida boolean, notificacao_id int8 not null, guardiao_id int8, usuario_id int8, primary key (notificacao_id))
create table telefones (usuario_id int8 not null, telefones varchar(255))
create table user_profiles (acesso_usuario_id int8 not null, profiles int4)
create table usuarios (id int8 not null, deletado boolean, email varchar(255), idade int4, nome varchar(255), sobrenome varchar(255), guardiao int8, primary key (id))
create table usuarios_grupos (grupo_id int8 not null, usuario_id int8 not null, primary key (grupo_id, usuario_id))
alter table if exists acessos add constraint UK_sf2xjw87m2duapmij7weceyo unique (nome_usuario)
alter table if exists usuarios add constraint UK_kfsp0s1tflm1cwlj8idhqsad0 unique (email)
alter table if exists acessos add constraint FK8c8btmoypfxbmo7qhejut09h1 foreign key (usuario_id) references usuarios
alter table if exists configuracoes add constraint FKaqyoopvqik6i443lig58el75f foreign key (usuario_id) references usuarios
alter table if exists enderecos add constraint FKbmhtlb81pj58hm1itutsrs1as foreign key (usuario_id) references usuarios
alter table if exists grupos_permitidos add constraint FKhcup9rw3a5ps6b44jdt8johmp foreign key (grupo_id) references grupos
alter table if exists grupos_permitidos add constraint FKg9vfrlucvvqm4b6hsnosw7on9 foreign key (usuario_id) references configuracoes
alter table if exists notificacoes add constraint FKddiebjubu4avylh9uuymdjbiw foreign key (notificacao_guardiao_notificacao_id) references notificacoes_guardiao
alter table if exists notificacoes add constraint FK3jcnk0ggmxklk5sjxm2plk5ml foreign key (usuario_id) references usuarios
alter table if exists notificacoes_grupos add constraint FKlw9ahg6v0ycrvqvshco2ykkvj foreign key (usuario_id) references usuarios
alter table if exists notificacoes_grupos add constraint FKni1mbv3yef45mj027op5v5scg foreign key (grupo_id) references grupos
alter table if exists notificacoes_grupos add constraint FK5jbwr05pbr0wqflswitd6dq2d foreign key (notificacao_id) references notificacoes
alter table if exists notificacoes_guardiao add constraint FKps4q5wbux0lusjmu30ktqubyb foreign key (guardiao_id) references usuarios
alter table if exists notificacoes_guardiao add constraint FKdgp8kcqvd9962m9t9xwqxi448 foreign key (notificacao_id) references notificacoes
alter table if exists notificacoes_guardiao add constraint FKseenvaorryfmfej1gh2slxejl foreign key (usuario_id) references usuarios
alter table if exists telefones add constraint FKb9h2hv9axg1t19g0x7b9ie4ls foreign key (usuario_id) references usuarios
alter table if exists user_profiles add constraint FK8wwrdg58jy5cd67yxfnq4xq9a foreign key (acesso_usuario_id) references acessos
alter table if exists usuarios add constraint FKca40993o8of6ed5usvtm9rbv foreign key (guardiao) references usuarios
alter table if exists usuarios_grupos add constraint FKm3qhj4o3gwf1l0qub0kddvldd foreign key (usuario_id) references usuarios
alter table if exists usuarios_grupos add constraint FKdh3qddmbja2u6ioqbpdscom1t foreign key (grupo_id) references grupos
