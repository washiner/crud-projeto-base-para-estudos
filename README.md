# 🧳 Agenda de Viagens API

API REST desenvolvida para aprender Spring Boot com a metodologia **Outside-In** — o frontend dita o contrato, o backend entrega.

Projeto base de estudos com evolução por branches — cada feature nova numa branch separada, mergeia na main via Pull Request.

---

## 📌 Visão Geral

| Camada | Tecnologia | Porta |
|---|---|---|
| Backend | Spring Boot 4.0.6 | 8080 |
| Documentação | Swagger / SpringDoc | 8080/swagger-ui.html |
| Banco de Dados | PostgreSQL 16 | 5432 |
| Admin do Banco | pgAdmin 4 | 5050 |

---

## 🌿 Branches

| Branch | O que tem |
|---|---|
| `main` | CRUD base sem DTOs |
| `feature/dtos` | DTOs de Request e Response ✅ |
| `feature/validacoes` | Bean Validation (em breve) |
| `feature/exceptions` | Exception Handler global (em breve) |
| `feature/flyway` | Migrations versionadas (em breve) |
| `feature/testes` | Testes unitários e de integração (em breve) |

---

## 📁 Estrutura do projeto

```
agenda-viagens/
└── src/main/java/com/washiner/agenda_viagens/
    ├── controller/
    │   └── ViagemController.java      ← porta de entrada
    ├── domain/
    │   ├── dto/
    │   │   ├── ViagemRequest.java     ← o que o front manda
    │   │   └── ViagemResponse.java    ← o que o back devolve
    │   ├── entity/
    │   │   └── ViagemModel.java       ← entidade do banco
    │   └── enums/
    │       └── StatusViagem.java      ← PLANEJADA, EM_ANDAMENTO, CONCLUIDA
    ├── repository/
    │   └── ViagemRepository.java      ← acessa o banco
    └── service/
        └── ViagemService.java         ← regras de negócio
```

---

## 🔄 Endpoints

| Método | Rota | Descrição |
|---|---|---|
| GET | `/viagem` | Lista todas as viagens |
| GET | `/viagem/{id}` | Busca viagem por ID |
| POST | `/viagem` | Cria nova viagem |
| PUT | `/viagem/{id}` | Atualiza viagem |
| DELETE | `/viagem/{id}` | Deleta viagem |

---

## 📦 DTOs

### ViagemRequest — o que o front manda
```json
{
  "destino": "Paris",
  "pais": "França",
  "dataPartida": "2026-10-01",
  "dataRetorno": "2026-10-15",
  "status": "PLANEJADA",
  "cpf": "123.456.789-00"
}
```

### ViagemResponse — o que o back devolve
```json
{
  "id": 1,
  "destino": "Paris",
  "pais": "França",
  "dataPartida": "2026-10-01",
  "dataRetorno": "2026-10-15",
  "status": "PLANEJADA"
}
```

> 🔒 O CPF entra pelo Request, é salvo no banco, mas **nunca sai** pelo Response.

---

## 🏗️ Arquivos principais

**`ViagemModel.java`**
```java
@Entity
@Table(name = "viagem")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class ViagemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private String pais;

    @Column(nullable = false)
    private LocalDate dataPartida;

    @Column(nullable = false)
    private LocalDate dataRetorno;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusViagem status;

    @Column(nullable = false)
    private String cpf;
}
```

**`ViagemRequest.java`**
```java
public record ViagemRequest(
    String destino,
    String pais,
    LocalDate dataPartida,
    LocalDate dataRetorno,
    StatusViagem status,
    String cpf
) {}
```

**`ViagemResponse.java`**
```java
public record ViagemResponse(
    Long id,
    String destino,
    String pais,
    LocalDate dataPartida,
    LocalDate dataRetorno,
    StatusViagem status
) {}
```

**`ViagemController.java`**
```java
@RestController
@RequestMapping("/viagem")
@RequiredArgsConstructor
public class ViagemController {

    private final ViagemService viagemService;

    @GetMapping
    public ResponseEntity<List<ViagemResponse>> listar() {
        return ResponseEntity.ok(viagemService.listarService());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViagemResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(viagemService.buscarPorID(id));
    }

    @PostMapping
    public ResponseEntity<ViagemResponse> criar(@RequestBody ViagemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(viagemService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ViagemResponse> atualizar(@PathVariable Long id, @RequestBody ViagemRequest request) {
        return ResponseEntity.ok(viagemService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        viagemService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
```

**`ViagemService.java`**
```java
@Service
@RequiredArgsConstructor
public class ViagemService {

    private final ViagemRepository viagemRepository;

    public List<ViagemResponse> listarService() {
        return viagemRepository.findAll()
                .stream()
                .map(viagem -> new ViagemResponse(
                    viagem.getId(), viagem.getDestino(), viagem.getPais(),
                    viagem.getDataPartida(), viagem.getDataRetorno(), viagem.getStatus()
                ))
                .toList();
    }

    public ViagemResponse buscarPorID(Long id) {
        ViagemModel viagem = viagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id não encontrado"));
        return new ViagemResponse(viagem.getId(), viagem.getDestino(), viagem.getPais(),
                viagem.getDataPartida(), viagem.getDataRetorno(), viagem.getStatus());
    }

    public ViagemResponse criar(ViagemRequest request) {
        ViagemModel viagem = new ViagemModel();
        viagem.setDestino(request.destino());
        viagem.setPais(request.pais());
        viagem.setDataPartida(request.dataPartida());
        viagem.setDataRetorno(request.dataRetorno());
        viagem.setStatus(request.status());
        viagem.setCpf(request.cpf());
        ViagemModel salvo = viagemRepository.save(viagem);
        return new ViagemResponse(salvo.getId(), salvo.getDestino(), salvo.getPais(),
                salvo.getDataPartida(), salvo.getDataRetorno(), salvo.getStatus());
    }

    public ViagemResponse atualizar(Long id, ViagemRequest request) {
        ViagemModel viagemBD = viagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id não encontrado"));
        viagemBD.setDestino(request.destino());
        viagemBD.setPais(request.pais());
        viagemBD.setDataPartida(request.dataPartida());
        viagemBD.setDataRetorno(request.dataRetorno());
        viagemBD.setStatus(request.status());
        ViagemModel salvo = viagemRepository.save(viagemBD);
        return new ViagemResponse(salvo.getId(), salvo.getDestino(), salvo.getPais(),
                salvo.getDataPartida(), salvo.getDataRetorno(), salvo.getStatus());
    }

    public void deletar(Long id) {
        ViagemModel viagem = viagemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id não encontrado"));
        viagemRepository.delete(viagem);
    }
}
```

---

## 🐳 Docker Compose

```yaml
services:
  postgres:
    image: postgres:16
    container_name: agenda-viagens-db
    environment:
      POSTGRES_DB: agenda_viagens
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin123
    ports:
      - "5432:5432"

  pgadmin:
    image: dpage/pgadmin4
    container_name: agenda-viagens-pgadmin
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@admin.com
      PGADMIN_DEFAULT_PASSWORD: admin123
    ports:
      - "5050:80"
    depends_on:
      - postgres
```

---

## ⚙️ application.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/agenda_viagens
spring.datasource.username=admin
spring.datasource.password=admin123

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🚀 Como rodar

```bash
# 1. Subir o banco
docker-compose up -d

# 2. Rodar o Spring Boot → pelo IntelliJ

# 3. Acessar o Swagger
http://localhost:8080/swagger-ui.html

# 4. Acessar o pgAdmin
http://localhost:5050
```

> ⚠️ Se o PC hibernar, reinicie o Spring Boot no IntelliJ.

---

## 📦 Dependências

- Spring Web
- Spring Boot DevTools
- Spring Data JPA
- Lombok
- PostgreSQL Driver
- SpringDoc OpenAPI (Swagger)
