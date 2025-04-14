const editor_nums = document.querySelector(".editor-nums");
const nav_a = document.querySelector(".nav a");

for (let i = 1; i <= 35; i++) {
  const p = document.createElement("p");
  p.textContent = i;
  editor_nums.appendChild(p);
}
nav_a.textContent = 'HOME';