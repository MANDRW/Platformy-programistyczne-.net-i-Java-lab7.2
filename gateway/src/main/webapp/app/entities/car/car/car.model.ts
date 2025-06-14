export interface ICar {
  id: number;
  name?: string | null;
  age?: number | null;
  isBroken?: boolean | null;
}

export type NewCar = Omit<ICar, 'id'> & { id: null };
