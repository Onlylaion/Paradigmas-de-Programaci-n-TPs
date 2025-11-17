% artista_base(Nombre, Roles).
% artista_contratado(Nombre).  

% Un base sabe un rol:
sabe_rol(artista_base(_, Roles), Rol) :-
    member(Rol, Roles).

% Un contratado NO sabe ningún rol:
sabe_rol(artista_contratado(_), _) :- 
    fail.

% Entrenamiento necesario cuando NADIE sabe el rol
entreno_necesario(Rol, Artistas) :-
    \+ (member(A, Artistas), sabe_rol(A, Rol)).

% Calculo total de entrenamientos:
entrenamientos_necesarios(Artistas, Roles, E) :-
    findall(1, (member(R, Roles), entreno_necesario(R, Artistas)), L),
    length(L, E).