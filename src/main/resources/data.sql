-- Inserir dados para a tabela User
INSERT INTO tb_user (username)
VALUES ('SwiftRunner23'),
       ('JazzyVibes88'),
       ('CodeWizard42'),
       ('ColorCanvas99'),
       ('CosmicExplorer77'),
       ('EnergizerRunner'),
       ('MusicExplorer123'),
       ('TechFanatic55'),
       ('ArtAdmirer321'),
       ('ScienceGeek456');

-- Inserir dados para a tabela Post
INSERT INTO tb_post (date_time, content, author, bubble)
VALUES ('2023-11-12T08:30:00', 'Explorando as últimas tendências de corrida! 🏃‍♂️ #RunningLife', 'SwiftRunner23',
        'SPORTS'),
       ('2023-11-12T10:45:00',
        'Mergulhando na melodia do novo álbum de Jazz. 🎺🎶 O que vocês estão ouvindo? #MusicLovers', 'JazzyVibes88',
        'MUSIC'),
       ('2023-11-12T12:15:00',
        'Desenvolvendo novas tecnologias disruptivas! 💻✨ Em breve, grandes novidades! #TechInnovation', 'CodeWizard42',
        'TECHNOLOGY'),
       ('2023-11-12T14:00:00',
        'Expressando a arte através das cores vibrantes! 🎨 Qual é a sua forma favorita de arte? #ArtisticExpression',
        'ColorCanvas99', 'ART'),
       ('2023-11-12T16:20:00',
        'Explorando os mistérios do universo! 🔬✨ Alguma descoberta emocionante para compartilhar? #ScienceEnthusiast',
        'CosmicExplorer77', 'SCIENCE');

-- Inserir dados para a tabela Comment
INSERT INTO tb_comment (content, author_id, post_id)
VALUES ('Incrível como você mantém o ritmo! 👏', 1, 1),
       ('Esse álbum é realmente envolvente! Recomendo a todos.', 2, 2),
       ('Estou ansioso para ver as inovações que estão por vir!', 2, 3),
       ('Adoro a maneira como você mistura as cores! 🌈', 5, 4),
       ('Ciência é fascinante! Compartilhe mais descobertas.', 1, 5);
