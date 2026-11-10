# Bar API

API REST para gerenciar um bar: clientes, garçons, comandas, drinks e ingredientes.

Feita só com Java 21 (JDK puro, `com.sun.net.httpserver`), JDBC, banco H2 embarcado em arquivo e Jackson para JSON. Sem frameworks.

## Modelo

- `Customer` (cliente) 1..N `Tab` (comanda)
- `Waiter` (garçom) 1..N `Tab`
- `Tab` N..N `Drink` (tabela `tab_drinks`)
- `Drink` N..N `Ingredient` (tabela `drink_ingredients`)
- `Person` (abstrata) é herdada por `Customer` e `Waiter`

Uma comanda com status `CLOSED` não aceita novos drinks.

## Requisitos

- JDK 21 ou superior (`java -version`)
- Não precisa instalar o Maven, o projeto usa o Maven Wrapper (`mvnw`)

## Compilar e gerar o jar

Linux/macOS:

```bash
./mvnw clean package
```

Windows:

```bash
mvnw.cmd clean package
```

O jar fica em `target/bar-api.jar`.

![build](screenshots/01-build.png)

## Executar

```bash
java -jar target/bar-api.jar
```

A API sobe em `http://localhost:8080`. Para usar outra porta: `java -jar target/bar-api.jar 9090`.

O banco é criado automaticamente em `data/bar.mv.db` na primeira execução. Para zerar os dados, apague a pasta `data`.

![run](screenshots/02-run.png)

## Endpoints

| Método | Rota | Descrição |
| --- | --- | --- |
| GET, POST | `/customers` | lista / cria clientes |
| GET, PUT, DELETE | `/customers/{id}` | busca / atualiza / remove |
| GET | `/customers/{id}/tabs` | comandas do cliente |
| GET, POST | `/waiters` | lista / cria garçons |
| GET, PUT, DELETE | `/waiters/{id}` | busca / atualiza / remove |
| GET | `/waiters/{id}/tabs` | comandas atendidas pelo garçom |
| GET, POST | `/tabs` | lista / abre comandas |
| GET, PUT, DELETE | `/tabs/{id}` | busca / atualiza (ex.: fechar) / remove |
| GET | `/tabs/{id}/drinks` | drinks da comanda |
| POST, DELETE | `/tabs/{id}/drinks/{drinkId}` | adiciona / remove drink da comanda |
| GET, POST | `/drinks` | lista / cria drinks |
| GET, PUT, DELETE | `/drinks/{id}` | busca / atualiza / remove |
| GET | `/drinks/{id}/ingredients` | ingredientes do drink |
| POST, DELETE | `/drinks/{id}/ingredients/{ingredientId}` | vincula / desvincula ingrediente |
| GET, POST | `/ingredients` | lista / cria ingredientes |
| GET, PUT, DELETE | `/ingredients/{id}` | busca / atualiza / remove |
| GET | `/ingredients/{id}/drinks` | drinks que usam o ingrediente |

## Exemplos

Criar cliente

![create customer](screenshots/03-create-customer.png)

Criar garçom

![create waiter](screenshots/04-create-waiter.png)

Criar drinks

![create drinks](screenshots/05-create-drinks.png)

Criar ingredientes

![create ingredients](screenshots/06-create-ingredients.png)

Abrir comanda

![create tab](screenshots/07-create-tab.png)

Listar drinks

![list](screenshots/08-list-all.png)

Relacionamentos 1..N

![one to many](screenshots/09-one-to-many.png)

Drinks na comanda (N..N entre comanda e drink)

![tab drinks](screenshots/10-tab-drinks.png)

Ingredientes do drink (N..N entre drink e ingrediente)

![drink ingredients](screenshots/11-drink-ingredients.png)

Fechar comanda

![update](screenshots/12-update.png)

Erros: comanda fechada, validação, não encontrado e conflito

![errors](screenshots/13-errors.png)

Remover

![delete](screenshots/14-delete.png)
