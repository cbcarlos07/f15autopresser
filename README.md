# F15 Auto Presser

Aplicação Java que pressiona automaticamente a tecla F15 a cada 120 segundos.

## Funcionalidades

A aplicação fica na bandeja do sistema do Windows com três opções:

- **Ativar**: Inicia o pressionamento automático da tecla F15 a cada 120 segundos
- **Desativar**: Interrompe o pressionamento automático
- **Sair**: Fecha a aplicação

## Como Usar

### Compilar
```bash
javac -d out src\Main.java
```

### Executar
```bash
java -cp out Main
```

### Usando a Aplicação

1. Execute a aplicação - ela aparecerá na bandeja do sistema com um ícone verde
2. Clique com o botão direito no ícone para ver o menu
3. Selecione "Ativar" para começar a pressionar F15 automaticamente
4. Selecione "Desativar" para parar
5. Selecione "Sair" para fechar a aplicação

**Dica**: Você também pode dar um duplo clique no ícone da bandeja para alternar rapidamente entre ativar e desativar.

## Requisitos

- Java 8 ou superior
- Sistema operacional Windows com suporte à bandeja do sistema

## Observações

- A tecla F15 será pressionada a cada 120 segundos (2 minutos) quando ativado
- A aplicação exibe notificações na bandeja informando sobre as ações realizadas
- O console exibe um log cada vez que a tecla F15 é pressionada

