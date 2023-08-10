Aplicativo de Gerenciamento de Solicitações de Pedidos ao Almoxarifado SENAI

O Aplicativo de Gerenciamento de Solicitações de Pedidos ao Almoxarifado SENAI é uma solução desenvolvida para a conclusão da disciplina de Programação Mobile do TÉCNICO EM DESENVOLVIMENTO DE SISTEMAS do SENAI/CE. O projeto foi desenvolvido em Java para dispositivos Android, com o objetivo de facilitar o processo de solicitação, gerenciamento e acompanhamento de pedidos de itens ao almoxarifado do SENAI. O aplicativo é projetado para atender a dois tipos de usuários: Administrador e Usuário.





Funcionalidades para Administradores:

Cadastro de produtos: Os administradores, identificados por seus emails com o domínio @senai.com, podem cadastrar novos produtos no sistema. Cada produto é associado a um ID exclusivo, nome e uma imagem representativa.
Visualização de pedidos: Os administradores têm acesso à lista completa de pedidos gerados pelos usuários. Eles podem ver o nome, ID e a quantidade de itens solicitados, bem como o email do solicitante.




Funcionalidades para Usuários:

Login: Os usuários podem fazer login no aplicativo usando seu nome de usuário (email) e senha. Os usuários com emails sem o domínio @senai.com têm acesso limitado.
Solicitação de itens: Usuários podem visualizar a lista de produtos cadastrados pelos administradores e solicitar os itens desejados.
Acompanhamento de pedidos: Os usuários podem acompanhar seus próprios pedidos, visualizando o nome, ID e a quantidade de itens solicitados.
Logout: Os usuários podem encerrar sua sessão a qualquer momento.


Infraestrutura e Requisitos Técnicos:

O aplicativo utiliza o serviço Firebase para autenticação e gerenciamento de dados. O Firebase Database é utilizado para armazenar informações de usuários, produtos e pedidos. A infraestrutura é projetada para escalabilidade, permitindo futuras expansões.




Plano de Implantação:

Configuração do Projeto Firebase: Configurar autenticação e Firebase Database.
Desenvolvimento e Testes: Desenvolver e testar o aplicativo no Android Studio.
Geração de APK: Compilar e gerar um APK assinado.
Testes em Dispositivos: Implantação em dispositivos Android para testes adicionais.
Testes de Aceitação: Realizar testes com usuários finais e coletar feedback.
Correções e Ajustes: Resolver problemas identificados durante os testes.
Empacotamento Final: Empacotar o aplicativo para distribuição.



Futuras Modificações:

Implementação de módulos de alteração e exclusão de itens.
Introdução de validações mais complexas.
Adição de políticas de privacidade detalhadas.
O Aplicativo de Gerenciamento de Solicitações de Pedidos ao Almoxarifado SENAI visa otimizar o processo de gerenciamento de pedidos e melhorar a eficiência das operações do almoxarifado, proporcionando uma experiência fluida para os usuários.