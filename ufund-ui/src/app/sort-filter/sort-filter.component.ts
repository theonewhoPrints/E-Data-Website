import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-sort-filter',
  templateUrl: './sort-filter.component.html',
  styleUrls: ['./sort-filter.component.css']
})
export class SortFilterComponent {
  @Output() sortEvent = new EventEmitter<string>();
  priceRange: number | null = null;

  sortByPrice(order: string): void {
    this.sortEvent.emit(order);
  }

  sortByPriceRange(): void {
    if (this.priceRange !== null && this.priceRange >= 0) {
      this.sortEvent.emit(`priceRange:${this.priceRange}`);
    }
  }

  
}
