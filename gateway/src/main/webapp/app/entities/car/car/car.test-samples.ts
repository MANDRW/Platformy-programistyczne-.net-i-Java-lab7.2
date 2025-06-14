import { ICar, NewCar } from './car.model';

export const sampleWithRequiredData: ICar = {
  id: 4218,
};

export const sampleWithPartialData: ICar = {
  id: 7884,
  age: 10529.74,
};

export const sampleWithFullData: ICar = {
  id: 22796,
  name: 'testimonial yahoo a',
  age: 11683.4,
  isBroken: true,
};

export const sampleWithNewData: NewCar = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
