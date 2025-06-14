import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { ICar } from '../car.model';

@Component({
  selector: 'jhi-car-detail',
  templateUrl: './car-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class CarDetailComponent {
  car = input<ICar | null>(null);

  previousState(): void {
    window.history.back();
  }
}
