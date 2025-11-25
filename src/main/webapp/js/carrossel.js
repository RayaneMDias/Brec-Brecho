document.addEventListener("DOMContentLoaded", () => {
    const track = document.querySelector('.slides');
    const items = document.querySelectorAll('.slides a');
    const btnPrev = document.querySelector('.slider-container .prev');
    const btnNext = document.querySelector('.slider-container .next');
 
    if (!track || items.length === 0 || !btnPrev || !btnNext) return;
 
    let currentIndex = 0;
 
    function getVisibleItems() {
        const width = window.innerWidth;
        if (width <= 600) return 1;
        if (width <= 900) return 2;
        return 3;
    }
 
    function updateCarousel() {
        const visibleItems = getVisibleItems();
        
        const itemWidth = items[0].getBoundingClientRect().width;
        
        const style = window.getComputedStyle(track);
        const gap = parseFloat(style.gap) || 0;
 
        const moveAmount = (itemWidth + gap) * currentIndex;
        
        track.style.transform = `translateX(-${moveAmount}px)`;
 
        const maxIndex = items.length - visibleItems;
 
        btnPrev.style.opacity = currentIndex === 0 ? "0.5" : "1";
        btnPrev.style.cursor = currentIndex === 0 ? "not-allowed" : "pointer";
 
        btnNext.style.opacity = currentIndex >= maxIndex ? "0.5" : "1";
        btnNext.style.cursor = currentIndex >= maxIndex ? "not-allowed" : "pointer";
    }
 
    btnNext.addEventListener('click', () => {
        const visibleItems = getVisibleItems();
        const maxIndex = items.length - visibleItems;
 
        if (currentIndex < maxIndex) {
            currentIndex++;
        }
        updateCarousel();
    });
 
    btnPrev.addEventListener('click', () => {
        if (currentIndex > 0) {
            currentIndex--;
        }
        updateCarousel();
    });
 
    window.addEventListener('resize', updateCarousel);
 
    updateCarousel();
});
 