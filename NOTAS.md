# 📓 Notas & Dicas — Projeto Base de Estudos

---

## 🌿 Fluxo Git Profissional

### Regra de ouro:
**A `main` é sempre limpa e funcionando. Nunca desenvolve direto nela.**

### Comandos do dia a dia:

```bash
# 1. Garante que está na main atualizada
git checkout main
git pull origin main

# 2. Cria branch nova pra feature
git checkout -b feature/nome-da-feature

# 3. Desenvolve, commita...
git add .
git commit -m "feat: descrição do que foi feito"

# 4. Sobe pro GitHub
git push origin feature/nome-da-feature

# 5. Abre Pull Request no GitHub e mergeia na main
```

### Comandos úteis do dia a dia:

```bash
# Ver o que mudou
git status

# Ver histórico de commits
git log

# Descartar TODAS as mudanças locais (volta igual ao GitHub)
git checkout -- .

# Descartar mudança de um arquivo específico
git restore src/main/java/com/washiner/Classe.java

# Ver diferença do que mudou
git diff

# Voltar pra main
git checkout main

# Atualizar local com o que está no GitHub
git pull origin main

# Adicionar arquivo novo que o Git não rastreia ainda
git add NOTAS.md
git add .  # adiciona tudo

# Commitar
git commit -m "docs: adiciona notas do projeto"

# Subir pro GitHub
git push origin main
# ou
git push origin feature/nome-da-branch

# Ver todas as branches
git branch -a

# Deletar branch local depois de mergear
git branch -d feature/nome-da-branch
```

### Fluxo visual:
```
main (limpa)
  ↓ checkout -b feature/dtos
feature/dtos → desenvolve → PR → merge
main (atualizada)
  ↓ checkout -b feature/validacoes
feature/validacoes → desenvolve → PR → merge
main (atualizada)
```

---

## 📝 Commits Semânticos

```
feat     → nova funcionalidade
fix      → correção de bug
docs     → documentação
refactor → refatoração sem mudar comportamento
style    → formatação, espaços (sem mudar lógica)
test     → testes
chore    → tarefas de build, configs
```

### Exemplos:
```
feat: implementa listagem de viagens
fix: corrige erro no buscar por id
docs: adiciona README do projeto
refactor: extrai lógica de validação para service
```

---

## 🔄 Fluxo Outside-In (como pensar)

**Nunca saia fazendo CRUD sem saber pra que serve.**

```
1. O que a tela precisa exibir?
2. Quais campos o JSON precisa ter?
3. Qual rota o backend precisa responder?
4. Aí você constrói: Controller → Service → Repository → Model
```

O código te guia — crie o método antes da classe existir e deixe a IDE te pedir o que falta.

---

## 🏗️ Estrutura das Camadas

```
Controller  → porta de entrada, recebe/devolve HTTP
Service     → regras de negócio
Repository  → acessa o banco
Model       → representa a tabela
```

### Regra de injeção:
```
Controller injeta Service ✅
Service injeta Repository ✅
Service injeta Controller ❌ (dependência circular!)
```

---

## 🎯 Insights importantes

### @PathVariable Long id → SEMPRE orElseThrow
```java
// Recebeu ID por parâmetro? Pode não existir no banco!
viagemRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Id não encontrado"));
```

### @RequestBody → NUNCA orElseThrow
```java
// Veio do front? Não precisa buscar no banco.
public ViagemModel criar(ViagemModel viagem) {
    return viagemRepository.save(viagem);
}
```

---

## 📊 ResponseEntity — Decoreba

```
GET    → ResponseEntity.ok(dado)              → 200
POST   → ResponseEntity.status(CREATED).body  → 201
DELETE → ResponseEntity.noContent().build()   → 204
```

### Analogia pra não esquecer:
- `ok()` → "aqui está o que você pediu" ✅
- `created()` → "criei, aqui está o que foi criado" 🆕
- `noContent()` → "fiz o que pediu, não tenho nada pra te dar" 🗑️

---

## 🌐 CORS

O navegador bloqueia chamadas entre portas diferentes (ex: Angular 4200 → Spring 8080).

**Solução rápida (estudo):**
```java
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class SeuController { }
```

**Solução profissional (mercado):**
```java
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
```

---

## 🐳 Como subir o projeto

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

## 🚀 Evolução planejada (próximas branches)

- [ ] `feature/dtos` → DTOs de request e response
- [ ] `feature/validacoes` → Bean Validation (@NotNull, @NotBlank)
- [ ] `feature/exceptions` → Exception Handler global
- [ ] `feature/flyway` → Migrations versionadas
- [ ] `feature/security` → Spring Security + JWT
- [ ] `feature/testes` → Testes unitários e de integração

---

## 💡 Dicas gerais

- **Nome de variável** → camelCase em Java (`dataPartida`), underscore no banco (`data_partida`). O JPA converte automaticamente.
- **BigDecimal** → sempre pra valores monetários. Nunca `double` ou `float`.
- **Integer** → para ano. Ano é número, você pode calcular com ele.
- **LocalDate** → para datas sem hora. **LocalDateTime** → para datas com hora.
- **@CreationTimestamp** → preenche automático quando cria. Não usar em campos que o usuário informa.
- **`ddl-auto=update`** → bom pra estudo. Em produção usar Flyway.
- **Enum com @Enumerated(EnumType.STRING)** → salva o nome no banco em vez do índice. Muito mais seguro.
