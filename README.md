# Music Affinity Clustering (Java + Graph Theory)

Aplicación de escritorio desarrollada en Java que utiliza algoritmos de Teoría de Grafos para agrupar usuarios basándose en sus gustos musicales. El sistema modela la afinidad como un grafo ponderado y aplica lógica de clustering para descubrir comunidades con intereses similares.

## Funcionalidades Principales:
- **Algoritmo de Clustering (Kruskal):** Implementación propia del algoritmo de Kruskal y estructura Union-Find para generar un Árbol Generador Mínimo (AGM) y dividirlo en $k$ grupos óptimos según la afinidad.
- **Visualización Interactiva de Grafos:** Integración con la librería **GraphStream** para renderizar en tiempo real los nodos (usuarios) y aristas (conexiones), permitiendo visualizar visualmente cómo se forman los grupos.
- **Arquitectura MVC Estricta:** Diseño desacoplado siguiendo el patrón Modelo-Vista-Controlador, separando la lógica algorítmica de la interfaz de usuario Swing.
- **Persistencia JSON:** Módulo de importación y exportación de datos de usuarios utilizando la librería **Gson**.
- **Métricas de Afinidad:** Cálculo matemático de similitud basado en vectores de 4 géneros (Tango, Folklore, Rock, Urbano).

## Tecnologías:
- **Lenguaje:** Java (JDK 17+).
- **GUI:** Java Swing (Nimbus Look & Feel) con diseño modular.
- **Librerías:** GraphStream (Visualización), Gson (Google JSON).
- **Estructuras de Datos:** Grafos, Aristas Ponderadas, Disjoint Set Union (DSU).

## Capturas:
![Interfaz Principal](screenshots/main_ui.png)
*Panel principal dividido con JSplitPane: Carga de usuarios a la izquierda y controles de agrupación a la derecha.*

![Visualización del Grafo](screenshots/graph_viz.png)
*Representación visual del AGM generado por GraphStream, donde cada color representa un cluster de afinidad.*

## Estructura del Proyecto:
- **/src/logica:** Implementación del núcleo matemático (Grafo, Arista, Kruskal, UnionFind).
- **/src/interfaz:** Ventanas y paneles Swing modularizados.
- **/src/controlador:** Orquestador de eventos entre la UI y el Modelo.
- **/data:** Archivos JSON de ejemplo para pruebas de carga masiva.

## Configuración y Ejecución:
1. Clonar el repositorio.
2. Importar el proyecto en Eclipse/IntelliJ IDEA.
3. Asegurar que las librerías `gs-core.jar` (GraphStream) y `gson.jar` estén en el Build Path.
4. Ejecutar la clase principal `VentanaPrincipal.java`.
5. Cargar el archivo `usuarios.json` y seleccionar la cantidad de grupos deseados.

---
**Autor:** [Matias Fauda] - [Tu LinkedIn]
*Proyecto académico realizado en colaboración con: Tobias Cabral, Mateo Smicht y Alan Valdiviezo (UNGS).*
