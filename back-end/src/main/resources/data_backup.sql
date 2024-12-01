INSERT INTO workflow (id, name, description) VALUES
(1, 'COOPERFILME_Workflow', 'Workflow para aprovação de roteiros da COOPERFILME');

INSERT INTO step (id, workflow_id, name, description, role_required, created_at) VALUES
(1, 1, 'aguardando_analise', 'Cliente envia o roteiro. Aguardando usuário assumir.', NULL, NOW()),
(2, 1, 'em_analise', 'Analista revisa o roteiro.', 'ANALISTA', NOW()),
(3, 1, 'aguardando_revisao', 'Aguardando Revisor assumir.', 'REVISOR', NOW()),
(4, 1, 'em_revisao', 'Revisor aponta erros ou ideias.', 'REVISOR', NOW()),
(5, 1, 'aguardando_aprovacao', 'Aguardando votos de aprovadores.', 'APROVADOR', NOW()),
(6, 1, 'em_aprovacao', 'Em votação para aprovação final.', 'APROVADOR', NOW()),
(7, 1, 'aprovado', 'Roteiro aprovado e finalizado.', NULL, NOW()),
(8, 1, 'recusado', 'Roteiro recusado. Finalizado.', NULL, NOW());

INSERT INTO step_transitions (id, workflow_id, from_step_id, to_step_id) VALUES
(1, 1, 1, 2), -- aguardando_analise -> em_analise
(2, 1, 2, 3), -- em_analise -> aguardando_revisao
(3, 1, 2, 8), -- em_analise -> recusado
(4, 1, 3, 4), -- aguardando_revisao -> em_revisao
(5, 1, 4, 5), -- em_revisao -> aguardando_aprovacao
(6, 1, 5, 6), -- aguardando_aprovacao -> em_aprovacao
(7, 1, 6, 7), -- em_aprovacao -> aprovado
(8, 1, 6, 8); -- em_aprovacao -> recusado


SELECT * FROM workflow;
SELECT * FROM step;
SELECT * FROM step_transitions;
SELECT * FROM step_reviews;


-- USUARIOS:

INSERT INTO eureka_test.users ("name", email, "role", "password")
VALUES
('João Silva', 'joao.analista@example.com', 'Analista', 'senha123'),
('Maria Souza', 'maria.revisor@example.com', 'Revisor', 'senha123'),
('Pedro Lima', 'pedro.aprovador1@example.com', 'Aprovador', 'senha123'),
('Ana Santos', 'ana.aprovador2@example.com', 'Aprovador', 'senha123'),
('Carlos Almeida', 'carlos.aprovador3@example.com', 'Aprovador', 'senha123');



