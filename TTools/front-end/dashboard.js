document.querySelector(".main").style.backgroundImage = 'url("./stock/1.png")';
// .replace("num", Math.floor(Math.random() * 7));

document.querySelectorAll('.draggable-div').forEach(div => {
  dragElement(div);
});

function dragElement(elmnt) {
  var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
  const header = elmnt.querySelector('.draggable-header') || elmnt;
  
  header.onmousedown = dragMouseDown;

  function dragMouseDown(e) {
    e = e || window.event;
    e.preventDefault();
    pos3 = e.clientX;
    pos4 = e.clientY;
    document.onmouseup = closeDragElement;
    document.onmousemove = elementDrag;
  }

  function elementDrag(e) {
    e = e || window.event;
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