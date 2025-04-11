package database;

import scenariocontext.ContextKey;

import java.time.LocalDateTime;

import static scenariocontext.ScenarioContext.getContext;

public class UserFactory {

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private LocalDateTime createdAt;


        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Users build() {
            Users user = new Users();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            user.setCreatedAt(createdAt != null ? createdAt : LocalDateTime.now());
            return user;
        }
    }

    public static Users createUser() {
        return new Builder()
                .withFirstName(getContext(ContextKey.USER_FIRST_NAME))
                .withLastName(getContext(ContextKey.USER_LAST_NAME))
                .withEmail(getContext(ContextKey.USER_EMAIL))
                .withPassword(getContext(ContextKey.USER_ENCRYPTED_PASSWORD))
                .build();
    }
}
