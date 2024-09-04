INSERT INTO `USERS` (`createdAt`, `email`, `name`, `password`,  `updatedAt`) VALUES
('2024-08-28 15:51:16', 'guillaume.beysson@gmail.com', 'Guillaume', '$2a$10$usHfvFymRUvTLItv1f2Khuzstst0KSw94CP5ivouvtGvR5gHJJdAS',  '2024-08-28 15:51:16'),
('2024-08-28 15:52:16', 'guillaume.beysson2@gmail.com', 'Julie', '$2a$10$0RLrHdq5fEa8F/5WaelKJe/NRBAzwVjx9kfWy2V2hFjVDi75vwJyS', '2024-08-28 15:52:16'),
('2024-08-28 15:53:16', 'guillaume.beysson3@gmail.com', 'Elio', '$2a$10$FZiGv5ch2dUqP6CNTFxCKu2KUX3jAjXothakd/fRzbgwRqWZ252hq',  '2024-08-28 15:53:16');

INSERT INTO `TOPICS` (`title`, `content`) VALUES
('Intelligence Artificielle', 'Explorez les concepts et applications de l\'IA, du machine learning aux réseaux de neurones et au-delà.'),
('Développement Web', 'Apprenez à créer des sites web modernes et responsifs en utilisant les technologies et frameworks les plus récents.'),
('Science des Données', 'Plongez dans l\'analyse de données, la visualisation et l\'utilisation d\'algorithmes pour extraire des insights de grands ensembles de données.'),
('Infrastructure Cloud', 'Comprenez les fondamentaux des services cloud comme AWS, Azure et Google Cloud pour déployer et gérer des applications.'),
('Cybersécurité', 'Apprenez l\'essentiel de la protection des systèmes et des données contre les menaces et vulnérabilités numériques.'),
('Développement Mobile', 'Explorez le développement d\'applications mobiles pour iOS et Android, en utilisant des outils comme Swift, Kotlin et Flutter.'),
('Blockchain', 'Comprenez la technologie derrière les cryptomonnaies et les applications décentralisées grâce à la blockchain.'),
('DevOps', 'Adoptez des pratiques qui comblent le fossé entre le développement et les opérations pour optimiser la livraison de logiciels.');

INSERT INTO `POSTS` (`title`, `content`, `createdAt`, `user_id`, `topic_id`) VALUES
('Introduction à la Science des Données avec Python', 'Découvrez les bases de la science des données et apprenez à utiliser les bibliothèques Python telles que NumPy et Pandas pour analyser des ensembles de données complexes, créer des visualisations interactives et modéliser des prédictions. Ce guide couvre également les pratiques de nettoyage des données, la manipulation des tableaux de données et lutilisation des outils de visualisation pour tirer des insights exploitables de vos analyses.', '2024-08-05', 1, 3),
('Créer des Sites Web Responsifs avec Bootstrap', 'Apprenez à créer des sites web adaptatifs en utilisant le framework Bootstrap. Ce tutoriel vous guide à travers les principes fondamentaux du design responsive, vous montrant comment utiliser les grilles, les composants et les utilitaires de Bootstrap pour développer rapidement des sites web élégants et accessibles sur tous types d’appareils, tout en optimisant le temps de développement grâce aux nombreux outils et fonctionnalités intégrés.', '2024-08-07', 2, 2),
('Démarrer avec le Machine Learning', 'Une introduction complète au machine learning, couvrant les algorithmes de base tels que la régression linéaire, les arbres de décision, et les réseaux de neurones. Ce guide pratique explique comment ces algorithmes sont appliqués dans divers secteurs pour résoudre des problèmes complexes, et comprend des exercices pour implémenter ces techniques avec des bibliothèques Python populaires comme Scikit-Learn et TensorFlow.', '2024-08-09', 1, 1),
('Sécuriser Votre Infrastructure Cloud', 'Comprenez les meilleures pratiques pour sécuriser les environnements cloud et protéger vos données sensibles contre les menaces potentielles. Ce guide détaillé explore la configuration de réseaux sécurisés, l’utilisation de la gestion des accès et identités (IAM), la mise en œuvre de la surveillance et de la journalisation, ainsi que les stratégies pour garantir la conformité avec les normes de sécurité en constante évolution.', '2024-08-10', 2, 4),
('Développer des Applications Mobiles avec Flutter', 'Explorez comment créer des applications mobiles multiplateformes performantes avec le SDK Flutter de Google. Ce tutoriel détaillé vous guide pas à pas, de la configuration initiale de l’environnement de développement à la création de l’interface utilisateur réactive et à la gestion des états, tout en vous montrant comment déployer vos applications sur iOS et Android sans effort supplémentaire.', '2024-08-12', 1, 6),
('Une Introduction à la Technologie Blockchain', 'Apprenez les concepts fondamentaux de la technologie blockchain, son fonctionnement et comment elle alimente les cryptomonnaies comme le Bitcoin. Ce guide explore également ses applications au-delà de la finance, notamment dans la gestion des chaînes d’approvisionnement, la sécurité des données, et les contrats intelligents, offrant un aperçu des opportunités de transformation numérique qu’elle représente.', '2024-08-13', 2, 7),
('Implémenter DevOps dans Votre Flux de Travail', 'Un guide complet pour intégrer les pratiques DevOps afin d’améliorer la collaboration entre les équipes de développement et d’exploitation, réduire les délais de livraison, et augmenter l’efficacité. Apprenez à automatiser les déploiements, à surveiller les applications en production et à adopter une culture de feedback continu pour une amélioration constante.', '2024-08-14', 1, 8),
('Introduction à l\'IA et aux Réseaux Neuroniques', 'Explorez les bases de l’intelligence artificielle, les principes des réseaux de neurones artificiels et comment ces technologies sont utilisées dans diverses applications, de la reconnaissance d’image aux systèmes de recommandation. Ce guide couvre également les concepts avancés tels que l’apprentissage profond, les architectures de réseaux complexes et les techniques d’entraînement pour développer des modèles d’IA performants.', '2024-08-15', 2, 1);


INSERT INTO `COMMENTS` (`content`, `createdAt`, `user_id`, `post_id`) VALUES
('Excellente introduction à la science des données!', '2024-08-06 09:30:00', 2, 1),
('Ce guide sur Bootstrap m\'a été vraiment utile.', '2024-08-08 14:20:00', 1, 2),
('Le machine learning est fascinant, j\'ai hâte d\'en apprendre plus.', '2024-08-10 18:00:00', 2, 3),
('La sécurité du cloud est tellement importante, merci pour ces éclaircissements.', '2024-08-11 11:45:00', 1, 4),
('Flutter semble être un outil puissant pour le développement mobile.', '2024-08-13 16:00:00', 2, 5),
('La blockchain est un domaine si intéressant, super article!', '2024-08-14 10:30:00', 1, 6),
('Les pratiques DevOps ont vraiment amélioré notre flux de travail.', '2024-08-15 12:45:00', 2, 7),
('L\'IA est l\'avenir, et cet article est un excellent point de départ.', '2024-08-16 15:20:00', 1, 8),
('Très bon article sur l\'introduction à la science des données, merci pour ces informations complètes.', '2024-08-06 10:00:00', 3, 1),
('J\'ai appris beaucoup de choses grâce à ce guide sur Bootstrap, continuez comme ça!', '2024-08-08 15:30:00', 3, 2),
('Merci pour cette introduction au machine learning, très clair et accessible.', '2024-08-10 19:00:00', 3, 3),
('Des conseils précieux sur la sécurité dans le cloud, c\'est essentiel à connaître.', '2024-08-11 12:30:00', 3, 4),
('Je commence à utiliser Flutter et ce guide est une aide précieuse!', '2024-08-13 17:00:00', 3, 5),
('Cet article sur la blockchain m\'a donné envie d\'en apprendre encore plus.', '2024-08-14 11:00:00', 3, 6),
('DevOps est clairement un must pour améliorer l\'efficacité des équipes, très bon guide!', '2024-08-15 13:30:00', 3, 7),
('L\'intelligence artificielle est un sujet complexe mais fascinant, merci pour cet article.', '2024-08-16 16:00:00', 3, 8);

INSERT INTO `SUBSCRIPTIONS` (`user_id`, `topic_id`) VALUES
(1, 1),
(1, 3),
(1, 4),
(1, 6),
(2, 2),
(2, 7);
