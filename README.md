# Proyecto SpringBoot-Next.js CRUD de Biblioteca

## Descripción
El proyecto SpringBoot-React CRUD de Biblioteca es una aplicación frontend desarrollada con Next.js y SpringBoot que permite gestionar una biblioteca de manera eficiente. Proporciona funcionalidades de CRUD (Crear, Leer, Actualizar, Eliminar) para libros, usuarios, categorías y préstamos. Los préstamos requieren la asociación de un libro y un usuario, asegurando un control integral de los préstamos de la biblioteca.

### FrontEnd
![Captura de pantalla 2024-02-17 161517](https://github.com/Leo0756/BibliotecaSpringBoot/assets/92804753/06042696-a7f9-43f0-98ff-a7f9c8f4bb1f)

#### Dialog
![image](https://github.com/Leo0756/BibliotecaSpringBoot/assets/92804753/29b4e62b-ffef-4a66-9fea-279248086ec5)

#### Fichas Individuales
![Captura de pantalla 2024-02-17 192811](https://github.com/Leo0756/BibliotecaSpringBoot/assets/92804753/4a46cd4d-977e-4e7d-aa3c-e0f77ad74137)
#### Notificaciones al realizar operaciones
![Captura de pantalla 2024-02-17 192901](https://github.com/Leo0756/BibliotecaSpringBoot/assets/92804753/eee10933-0428-4cb5-b923-759d8387a8e8)


### Tecnologías Utilizadas
- **Next.js**: Framework de React para aplicaciones web que ofrece renderizado del lado del servidor y capacidades de enrutamiento.
- **Axios**: Cliente HTTP que facilita las solicitudes y respuestas HTTP.
- **PrimeReact**: Librería de componentes de UI para React que proporciona una amplia gama de componentes de interfaz de usuario.

### Características Principales
- **Menú de Navegación Intuitivo**: La aplicación cuenta con un menú de navegación intuitivo que permite a los usuarios acceder fácilmente a las secciones de préstamos, usuarios, libros y categorías.
- **Gestión Completa de Registros**: Proporciona operaciones CRUD completas para libros, usuarios, categorías y préstamos, lo que permite a los usuarios realizar fácilmente tareas como agregar, editar, ver y eliminar registros.
- **Diálogos Modales**: Al hacer clic en cada elemento del menú, se abre un diálogo modal interactivo que muestra todos los registros de la entidad correspondiente. Esto facilita la visualización y la gestión de los registros.
- **Edición en Tiempo Real**: Cada entidad en los diálogos modales permite la edición en tiempo real de los registros. Al hacer clic en el botón de edición, se abre un nuevo diálogo que muestra la ficha individual del registro, lo que permite realizar cambios y guardarlos de forma inmediata.
- **Interfaz de Usuario Responsiva**: La aplicación cuenta con una interfaz de usuario responsive que se adapta a diferentes tamaños de pantalla y dispositivos, garantizando una experiencia de usuario consistente y agradable en cualquier dispositivo.

### Ejemplos de Código con Axios

#### Eliminar un Libro
```javascript
import axios from "axios";

export const eliminarLibro = (id, libros, setLibros, setVisibleFichaLibro, toast) => {
    axios.delete(`http://localhost:8080/biblioteca/libros/${id}`)
        .then(response => {
            const nuevosLibros = libros.filter(libro => libro.id !== id);
            setLibros(nuevosLibros);
            setVisibleFichaLibro(false);
            toast.current.show({ severity: 'success', summary: 'Éxito', detail: 'Libro eliminado correctamente', life: 3000 });
        })
        .catch(error => {
            console.error("Error al eliminar libro:", error);
            toast.current.show({ severity: 'error', summary: 'Error', detail: 'Error al eliminar el libro', life: 3000 });
        });
};
```
#### Actualizar un Libro
```javascript
export const actualizarLibro = (id, nuevoLibro, libros, setLibros, toast) => {
    // Realizar la solicitud PUT para actualizar el libro por su ID
  
    axios.put(`http://localhost:8080/biblioteca/libros/${id}`, nuevoLibro)
        .then(() => {
            // Actualizar el estado de los libros con el nuevo libro
            const librosActualizados = libros.map(libro => {
                if (libro.id === id) {
                    return { ...nuevoLibro, id: libro.id }; // Sustituir el libro existente con el nuevoLibro
                }
                return libro;
            });
            
            setLibros(librosActualizados);
            toast.current.show({ severity: 'success', summary: 'Éxito', detail: 'Libro actualizado correctamente', life: 3000 });
        })
        .catch(error => {
            console.error("Error al actualizar libro:", error);
            toast.current.show({ severity: 'error', summary: 'Error', detail: 'Error al actualizar el libro', life: 3000 });
        });
};
```
#### Guardar Nuevo Libro
```javascript
export const guardarNuevoLibro = (libroInput, categorias, categoriaSeleccionadaLibro, libros, setLibros, setVisibleCrearLibro, toast, setLibroInput, setCategoriaSeleccionadaLibro) => {
    const nuevoLibroConCategoria = { ...libroInput, categoria: categorias.find(cat => cat.id === categoriaSeleccionadaLibro) };
    axios.post("http://localhost:8080/biblioteca/libros", nuevoLibroConCategoria)
        .then(response => {
            setLibros([...libros, response.data]);
            setVisibleCrearLibro(false);
            toast.current.show({ severity: 'success', summary: 'Éxito', detail: 'Libro creado correctamente', life: 3000 });
            setLibroInput({
                id: '',
                nombre: '',
                autor: '',
                editorial: '',
                categoria: {}
            });
            setCategoriaSeleccionadaLibro("");
        })
        .catch(error => {
            console.error("Error al crear libro:", error);
            toast.current.show({ severity: 'error', summary: 'Error', detail: 'Error al crear el libro', life: 3000 });
        });
};
```
### Errores durante el Desarrollo y Soluciones

Aqui todos los errores y soluciones encontrados durante el desarrollo de la aplicacion:

#### Error de CORS en las Peticiones Axios

El error de Cross-Origin Resource Sharing (CORS) es común al realizar solicitudes HTTP desde un dominio diferente al servidor.

Para solucionar el error de CORS,configuramos SpringBoot para permitir solicitudes desde el dominio del frontend. Esto se puede hacer configurando los encabezados CORS en Spring Boot.

Añadiendo esta linea se soluciona el error:

```java
@RestController
@RequestMapping("/biblioteca/categorias")
@CrossOrigin(origins = "http://localhost:3000")

public class CategoriaController {

    @Autowired // Implementación de la interfaz IRepositoryCategoria.
    IRepositoryCategoria repositoryCategoria;
```

### Error con el formato de la fecha

Durante la revisión del CRUD de préstamo, tuvimos complicaciones a la hora de introducir datos en la tabla del mismo, y el problema surgió cuando en el código generado con JPA el formato de la fecha era **LocalDateTime**, esto causaba que fuera incomoda la inserción del préstamo, además de que proporcionaba información innecesaria como son las horas, minutos y segundos.

Lo único que tuvimos que hacer fue cambiar el formato de **LocalDateTime** a **LocalDate**, de esta manera se pueden insertar los datos de una manera mucho más cómoda y no proporciona información innecesaria.

Código con el campo "fecha" con el objeto cambiado:

```java
 @Basic
    @Column(name = "fechaprestamo", nullable = true)
    private LocalDate fechaPrestamo;
```

