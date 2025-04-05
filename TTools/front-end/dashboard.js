let frameworks = [];
let databases = [];
let planguages = [];
let sudoplanguages = [];
let webdevtools = [];

const main = document.querySelector(".main");
main.style.backgroundImage = 'url("./stock/1.png")';

document.addEventListener('DOMContentLoaded', async function() {
  const loaders = [
    { name: 'frameworks', url: './json/frameworks.json' },
    { name: 'databases', url: './json/databases.json' },
    { name: 'planguages', url: './json/planguages.json' },
    { name: 'sudoplanguages', url: './json/sudoplanguages.json' },
    { name: 'webdevtools', url: './json/webdevtools.json' }
  ];

  try {
    const results = await Promise.all(
      loaders.map(async loader => {
        const response = await fetch(loader.url);
        if (!response.ok) throw new Error(`Failed to load ${loader.url}`);
        return {
          name: loader.name,
          data: await response.json()
        };
      })
    );

    results.forEach(result => {
      switch(result.name) {
        case 'frameworks':
          frameworks = result.data.frameworks || [];
          break;
        case 'databases':
          databases = result.data.databases || [];
          break;
        case 'planguages':
          planguages = result.data.programmingLanguages || result.data.planguages || [];
          break;
        case 'sudoplanguages':
          sudoplanguages = result.data.markupAndConfigLanguages || result.data.sudoplanguages || [];
          break;
        case 'webdevtools':
          webdevtools = result.data.webDevTools || result.data.webdevtools || [];
          break;
      }
    });

    console.log('All data loaded successfully'); 
  } catch (error) {
    console.error('Error loading data:', error);
  }
});

function getImageName(frameworkName) {
  const map = {
    // Original frameworks
    'Angular': 'angular',
    'React': 'react',
    'Vue.js': 'vue-js',
    'Spring Boot': 'spring-boot',
    'Django': 'django',
    'Express.js': 'express-js',
    'Ruby on Rails': 'ruby-on-rails',
    'Laravel': 'laravel',
    'ASP.NET Core': 'dotnet-core',
    'NestJS': 'nestjs',
    'Node.js': 'nodejs',
    'Svelte': 'svelte',
    
    // Additional frameworks
    'FastAPI': 'fastapi',
    'Gin': 'gin',
    'Actix': 'actix-web',
    'Next.js': 'nextjs',
    'Nuxt.js': 'nuxtjs',
    'Deno': 'deno',
    'Remix': 'remix',
    'SvelteKit': 'sveltekit',
    'Astro': 'astro',
    
    // Databases
    'PostgreSQL': 'postgresql',
    'MySQL': 'mysql',
    'Microsoft SQL Server': 'sqlserver',
    'Oracle DB': 'oracle',
    'SQLite': 'sqlite',
    'MongoDB': 'mongodb',
    'CouchDB': 'couchdb',
    'Redis': 'redis',
    'Cassandra': 'cassandra',
    'Neo4j': 'neo4j',
    'CockroachDB': 'cockroachdb',
    'Firestore': 'firestore',
    'DynamoDB': 'dynamodb',
    'InfluxDB': 'influxdb',
    'TimescaleDB': 'timescaledb',
    'Elasticsearch': 'elasticsearch',
    'Apache Solr': 'solr',

    // Programming Languages (planguages)
    'Java': 'java',
    'Python': 'python',
    'C': 'c',
    'C#': 'csharp',
    'C++': 'cpp',
    'JavaScript': 'javascript',
    'Kotlin': 'kotlin',
    'Go': 'go',
    'Rust': 'rust',
    'Swift': 'swift',
    'Ruby': 'ruby',
    'PHP': 'php',
    'TypeScript': 'typescript',
    'Lua': 'lua',
    'Dart': 'dart',
    'Zig': 'zig',
    'Elixir': 'elixir',
    'Julia': 'julia',
    'R': 'r',
    'Haskell': 'haskell',
    'Scala': 'scala',
    'Perl': 'perl',
    'Groovy': 'groovy',
    'COBOL': 'cobol',
    'Fortran': 'fortran',
    'OCaml': 'ocaml',
    'Clojure': 'clojure',
    'Erlang': 'erlang',
    'Bash': 'bash',
    'PowerShell': 'powershell',
    'SQL': 'sql',
    'Assembly': 'assembly',

    // Markup/Special Languages (sudoplanguages)
    'HTML': 'html',
    'XML': 'xml',
    'Markdown': 'markdown',
    'CSS': 'css',
    'SASS': 'sass',
    'SCSS': 'scss',
    'GQL': 'gql',
    'CQL': 'cql',
    'JSON': 'json',
    'YAML': 'yaml',
    'TOML': 'toml',
    'JSX': 'jsx',
    'HTMX': 'htmx',
    'Regex': 'regex',
    'Dockerfile': 'dockerfile',

    // Web Development Tools (webdevtools)
    'Git': 'git',
    'npm': 'npm',
    'Yarn': 'yarn',
    'pip': 'pip',
    'Maven': 'maven',
    'Webpack': 'webpack',
    'Vite': 'vite',
    'Gradle': 'gradle',
    'Docker': 'docker',
    'Kubernetes': 'kubernetes',
    'Vercel': 'vercel',
    'Netlify': 'netlify',
    'Jest': 'jest',
    'Cypress': 'cypress',
    'Postman': 'postman',
    'Figma': 'figma',
    'Adobe XD': 'adobe-xd',
    'Storybook': 'storybook',
    'Lighthouse': 'lighthouse',
    'PageSpeed Insights': 'pagespeed-insights',
    'WordPress': 'wordpress',
    'Strapi': 'strapi',
    'Sanity': 'sanity',
    'Google Analytics': 'google-analytics',
    'Hotjar': 'hotjar',
    'SonarQube': 'sonarqube',
    'Snyk': 'snyk'
  };
  
  return map[frameworkName] || undefined;
}

function dragElement(elmnt) {
  let pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
  const header = elmnt.querySelector('.draggable-header') || elmnt;
  
  header.onmousedown = dragMouseDown;

  function dragMouseDown(e) {
    e.preventDefault();
    pos3 = e.clientX;
    pos4 = e.clientY;
    document.onmouseup = closeDragElement;
    document.onmousemove = elementDrag;
  }

  function elementDrag(e) {
    e.preventDefault();
    
    const main = document.querySelector('.main');
    const mainRect = main.getBoundingClientRect();
    const asideWidth = document.querySelector('.aside').offsetWidth;
    
    pos1 = pos3 - e.clientX;
    pos2 = pos4 - e.clientY;
    pos3 = e.clientX;
    pos4 = e.clientY;
    
    let newTop = elmnt.offsetTop - pos2;
    let newLeft = elmnt.offsetLeft - pos1;
    
    const minLeft = asideWidth;
    const maxLeft = mainRect.width - elmnt.offsetWidth;
    const minTop = 0;
    const maxTop = mainRect.height - elmnt.offsetHeight;
    
    newLeft = Math.max(minLeft, Math.min(newLeft, maxLeft));
    newTop = Math.max(minTop, Math.min(newTop, maxTop));
    
    elmnt.style.top = newTop + "px";
    elmnt.style.left = newLeft + "px";
  }

  function closeDragElement() {
    document.onmouseup = null;
    document.onmousemove = null;
  }
}

function addContainer(title, values) {
  const container = document.createElement('div');
  container.className = 'draggable-div';
  container.innerHTML = `
    <div class="draggable-header">
      <div class="draggable-header-inside">
        <p>${title}</p>
        <button type="button" class="draggable-btn">^</button>
        <button type="button" class="draggable-btn2">X</button>
      </div>
      <ul>
        ${values.map(f => `
          <li class="draggable__li">
            <label>
              <input type="checkbox" class="draggable-check">
              <img src="./${title.toString().toLowerCase()}/${getImageName(f.name)}.png" alt="${f.name}">
            </label>
          </li>
        `).join('')}
      </ul>
    </div>
  `;

  main.appendChild(container);

  dragElement(container);

  const btn = container.querySelector('.draggable-btn');
  btn.addEventListener("click", () => {
    document.querySelectorAll('.draggable-div').forEach(div => {
      div.style.zIndex = div === container ? '2' : '1';
    });
  });
  const btn2 = container.querySelector(".draggable-btn2");
  btn2.addEventListener("click", () => {
    btn2.closest(".draggable-div").remove();
  });
}

document.querySelector('.aside__form2').addEventListener('submit', (e) => {
  e.preventDefault();
  const value = document.querySelector('#select__asi').value;
  console.log("Value = "+value);
  switch(value) {
    case "FRAMEWORKS":
      addContainer(value, frameworks);
      break;
    case "PLANGUAGES":
      addContainer(value, planguages);
      break;
    case "SUDOPLANGUAGES":
      addContainer(value, sudoplanguages);
      break;
    case "WEBDEVTOOLS":
      addContainer(value, webdevtools);
      break;
    case "DATABASES":
      addContainer(value, databases);
      break;
  }
});

document.addEventListener('change', function(e) {
  if (e.target.classList.contains('draggable-check')) {
    const li = e.target.closest('.draggable__li');
    li.style.backgroundColor = e.target.checked ? '#46383c' : '';
  }
});