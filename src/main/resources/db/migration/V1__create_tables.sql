-- Tabela de Departamentos
CREATE TABLE departments (
                             id UUID PRIMARY KEY,
                             name VARCHAR(100) NOT NULL UNIQUE,
                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Cargos
CREATE TABLE positions (
                           id UUID PRIMARY KEY,
                           title VARCHAR(100) NOT NULL UNIQUE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Colaboradores
CREATE TABLE employees (
                           id UUID PRIMARY KEY,
                           name VARCHAR(200) NOT NULL,
                           email VARCHAR(150) NOT NULL UNIQUE,
                           department_id UUID NOT NULL,
                           position_id UUID NOT NULL,
                           created_at TIMESTAMP NOT NULL,
                           status VARCHAR(20) NOT NULL CHECK (status IN ('ATIVO', 'INATIVO')),

    -- Chaves estrangeiras
                           CONSTRAINT fk_employee_department
                               FOREIGN KEY (department_id)
                                   REFERENCES departments(id)
                                   ON DELETE RESTRICT,

                           CONSTRAINT fk_employee_position
                               FOREIGN KEY (position_id)
                                   REFERENCES positions(id)
                                   ON DELETE RESTRICT
);

-- Comentários para documentação
COMMENT ON TABLE departments IS 'Tabela de departamentos da empresa';
COMMENT ON TABLE positions IS 'Tabela de cargos/funções dos colaboradores';
COMMENT ON TABLE employees IS 'Tabela de colaboradores da empresa';