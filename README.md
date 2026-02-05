# 🏢 Portal de Recursos Humanos (Registro de Ponto)

Sistema de simulação de um portal corporativo para gerenciamento de funcionários e controle de ponto eletrônico. O projeto foca na aplicação de **Herança**, **Polimorfismo** e **Tratamento de Exceções** para validar regras de negócio complexas.

## 🎯 Objetivo
Desenvolver uma solução em Java que permita o cadastro de diferentes perfis de colaboradores e realize o controle rigoroso de jornada de trabalho, aplicando restrições específicas para cada cargo e validando a consistência dos horários lançados.

## ⚙️ Funcionalidades

O sistema oferece um menu interativo com as seguintes operações:

### 👥 Gestão de Funcionários
- **Cadastro:** Criação de funcionários com *ID* único e *Nome*.
- **Hierarquia de Cargos:** Suporte a 5 tipos de colaboradores:
  - Gerente
  - Coordenador
  - Analista
  - Assistente
  - Estagiário
- **Listar Funcionários:** Apresentar uma lista dos funcionários e suas informações básicas.  
- **Busca de Funcionários:** Apresentar todos os registros de dias trabalhados de um funcionário e função de remoção o funcionário através do identificador (ID).

### 🕒 Registro de Ponto e Validações
- **Lançamento de Horas:** Input de data, hora de entrada e hora de saída.
- **Cálculo Automático:** Desconto automático de 1 hora de almoço no total trabalhado.
- **Histórico:** Listagem completa dos registros de ponto de um funcionário específico.

## 🛡️ Regras de Negócio e Tratamento de Erros

O diferencial deste projeto é a robustez das validações implementadas. O sistema **lança exceções** e impede a operação nos seguintes casos:

1.  **Restrição de Cargo:**
    * *Gerentes* e *Estagiários* são bloqueados de registrar ponto (Isenção de controle de jornada).
2.  **Janela de Funcionamento:**
    * Entradas permitidas apenas após as **06:00**.
    * Saídas permitidas apenas até as **22:00**.
3.  **Consistência Temporal:**
    * Bloqueio caso o horário de saída seja anterior ou igual à entrada.
4.  **Limite de Horas Extras:**
    * **Analistas e Assistentes:** Máximo de 3 horas extras/dia.
    * **Coordenadores:** Máximo de 5 horas extras/dia.
    * O sistema calcula a jornada padrão e rejeita o registro se o excedente ultrapassar o limite do cargo.

## 🛠️ Tecnologias e Conceitos

- **Java (JDK Atualizado)**
- **Orientação a Objetos:**
    - **Herança:** Classe base `Funcionario` e subclasses especializadas.
    - **Polimorfismo:** Comportamentos distintos de validação para cada cargo.
    - **Encapsulamento:** Proteção dos dados sensíveis do funcionário.
- **Collections API:** Uso de `TreeMap` e `LinkedHashMap` para gerenciamento dinâmico de registros e funcionários.
- **Tratamento de Exceções:** Uso de `try-catch` para garantir que regras de negócio violadas não quebrem a execução do programa.
- **LocalDate/LocalTime API:** Manipulação precisa de horários.

## 🚀 Como executar

1. Certifique-se de ter o **Java** instalado.
2. Clone o repositório:
```bash
git clone https://github.com/CarlosOliverDev/sistema-gestao-rh.git
```
3. Importe o projeto na sua IDE (IntelliJ, Eclipse ou VS Code).
4. Execute a classe Main.java (ou a classe que contém o método main).
