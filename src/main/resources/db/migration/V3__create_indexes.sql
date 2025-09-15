-- Índices para melhor performance nas consultas frequentes

-- Índices para employees
CREATE INDEX idx_employees_department_id ON employees(department_id);
CREATE INDEX idx_employees_position_id ON employees(position_id);
CREATE INDEX idx_employees_status ON employees(status);
CREATE INDEX idx_employees_email ON employees(email);
CREATE INDEX idx_employees_created_at ON employees(created_at);

-- Índices para departments
CREATE INDEX idx_departments_name ON departments(name);

-- Índices para positions
CREATE INDEX idx_positions_title ON positions(title);

-- Índice composto para buscas por departamento e status
CREATE INDEX idx_employees_department_status ON employees(department_id, status);