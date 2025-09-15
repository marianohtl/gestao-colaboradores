-- Inserir departamentos iniciais
INSERT INTO departments (id, name) VALUES
                                       ('11111111-1111-1111-1111-111111111111', 'TI'),
                                       ('22222222-2222-2222-2222-222222222222', 'RH'),
                                       ('33333333-3333-3333-3333-333333333333', 'Financeiro');

-- Inserir cargos iniciais
INSERT INTO positions (id, title) VALUES
                                      ('44444444-4444-4444-4444-444444444444', 'Engenheiro Júnior'),
                                      ('55555555-5555-5555-5555-555555555555', 'Engenheiro Pleno'),
                                      ('66666666-6666-6666-6666-666666666666', 'Analista Sênior'),
                                      ('77777777-7777-7777-7777-777777777777', 'Gerente de RH'),
                                      ('88888888-8888-8888-8888-888888888888', 'Analista Financeiro');

-- Inserir colaboradores iniciais
INSERT INTO employees (id, name, email, department_id, position_id, created_at, status) VALUES
                                                                                            ('99999999-9999-9999-9999-999999999999', 'Joana Silva', 'joana@banco.com', '11111111-1111-1111-1111-111111111111', '55555555-5555-5555-5555-555555555555', '2024-01-10T10:00:00', 'ATIVO'),
                                                                                            ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'Carlos Santos', 'carlos@banco.com', '11111111-1111-1111-1111-111111111111', '44444444-4444-4444-4444-444444444444', '2024-01-15T14:30:00', 'ATIVO'),
                                                                                            ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'Maria Oliveira', 'maria@banco.com', '22222222-2222-2222-2222-222222222222', '77777777-7777-7777-7777-777777777777', '2024-01-20T09:15:00', 'ATIVO'),
                                                                                            ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'Pedro Costa', 'pedro@banco.com', '33333333-3333-3333-3333-333333333333', '88888888-8888-8888-8888-888888888888', '2024-01-25T16:45:00', 'ATIVO');