module.exports = {
  extends: ['@commitlint/config-conventional'],
  rules: {
    // Tipos de commit permitidos
    'type-enum': [
      2,
      'always',
      [
        'feat',      // nueva funcionalidad
        'fix',       // corrección de errores
        'docs',      // cambios en la documentación
        'style',     // formato, espacios, punto y coma, etc.
        'refactor',  // refactorización del código
        'test',      // agregar o modificar pruebas
        'chore',     // tareas que no afectan el código ejecutable
        'perf',      // mejoras de rendimiento
        'ci',        // cambios en configuración de CI
        'revert',    // revertir un commit anterior
        'hotfix'     // hotfix urgente
      ]
    ],

    // Scopes permitidos
    'scope-enum': [
      2,
      'always',
      [
        'auth',       // autenticación
        'api',        // capa API o endpoints
        'ui',         // interfaz de usuario
        'db',         // base de datos
        'config',     // configuración del proyecto
        'deps',       // dependencias
        'tests',      // pruebas automatizadas
        'infra',      // infraestructura (docker, nginx, etc.)
        'docs',       // documentación
        'core',       // lógica central del negocio
        'build',       // sistema de build
        'goty'
      ]
    ],

    'scope-empty': [2, 'never'], // obliga a definir un scope
    'subject-case': [0],         // sin validación de formato del mensaje
  },
};