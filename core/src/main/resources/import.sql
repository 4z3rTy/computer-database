INSERT INTO `authority`(`name`, `id`) VALUES ('ROLE_ADMIN', 1);

INSERT INTO `authority`(`name`, `id`) VALUES ('ROLE_USER', 2);

INSERT INTO `user` (`id`, `username`, `password`, `dateCreated`) VALUES (1,'myadmin','$2y$12$ewzLOSXwaQ8ArzkcHAo0zeyx0V3LLySMW5Y34StjkNzhrn9SPfa02','2015-11-15 22:14:54');

INSERT INTO `user` (`id`, `username`, `password`, `dateCreated`) VALUES (2,'myuser','$2y$12$8GJHgk2Ggr7Xl/aJL0z2xuXp/ObZESaVr3e3mpIEpifTl8/d9q.UW','2015-11-16 22:14:54');

INSERT INTO `user_authority`(`authority_id`, `user_id`) VALUES (1, 1);

INSERT INTO `user_authority`(`authority_id`, `user_id`) VALUES (2, 2);

