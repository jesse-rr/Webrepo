document.querySelectorAll(".form-check").forEach((formCheckElement) => {
  let checkboxState = 0;
  
  formCheckElement.addEventListener("click", (event) => {
    const checkbox = formCheckElement.querySelector(".form-check-input");

    if (event.target === checkbox || event.target === formCheckElement) {
      if (checkboxState === 0) {
        formCheckElement.style.backgroundColor = "lightgreen";
      } else if (checkboxState === 1) {
        formCheckElement.style.backgroundColor = "red";
      } else if (checkboxState === 2) {
        formCheckElement.style.backgroundColor = "var(--color-3-1)";
      }

      checkboxState = (checkboxState + 1) % 3;
    }
  });
});

function updateSliderValue(sliderMinId, sliderMaxId, minValueId, maxValueId) {
  const sliderMin = document.getElementById(sliderMinId);
  const sliderMax = document.getElementById(sliderMaxId);
  const minValue = document.getElementById(minValueId);
  const maxValue = document.getElementById(maxValueId);

  function syncSliders() {
    if (parseFloat(sliderMin.value) > parseFloat(sliderMax.value)) {
      sliderMax.value = sliderMin.value;
    }
    if (parseFloat(sliderMax.value) < parseFloat(sliderMin.value)) {
      sliderMin.value = sliderMax.value;
    }
  }

  sliderMin.addEventListener('input', function() {
    syncSliders();
    minValue.textContent = formatMetadata(sliderMin.value);
  });

  sliderMax.addEventListener('input', function() {
    syncSliders();
    maxValue.textContent = formatMetadata(sliderMax.value);
  });
}

function formatMetadata(number) {
  if (number >= 1000000) {
    return parseInt(number / 1000000) + "." + parseInt((number % 1000000) / 100000) + "M";
  } else if (number >= 1000) {
    return parseInt(number / 1000) + "." + parseInt((number % 1000) / 100) + "K";
  }
  return number;
}

updateSliderValue('ratingMin', 'ratingMax', 'ratingMinValue', 'ratingMaxValue');
updateSliderValue('chapterQuantityMin', 'chapterQuantityMax', 'chapterQuantityMinValue', 'chapterQuantityMaxValue');
updateSliderValue('viewsQuantityMin', 'viewsQuantityMax', 'viewsQuantityMinValue', 'viewsQuantityMaxValue');
updateSliderValue('bookmarksQuantityMin', 'bookmarksQuantityMax', 'bookmarksQuantityMinValue', 'bookmarksQuantityMaxValue');
updateSliderValue('reviewQuantityMin', 'reviewQuantityMax', 'reviewQuantityMinValue', 'reviewQuantityMaxValue');
