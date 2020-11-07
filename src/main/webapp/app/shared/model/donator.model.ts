import { Moment } from 'moment';
import { ITreat } from 'app/shared/model/treat.model';

export interface IDonator {
  id?: number;
  name?: string;
  surname?: string;
  paymentDate?: Moment;
  amount?: number;
  message?: string;
  anonymous?: boolean;
  donators?: ITreat;
}

export class Donator implements IDonator {
  constructor(
    public id?: number,
    public name?: string,
    public surname?: string,
    public paymentDate?: Moment,
    public amount?: number,
    public message?: string,
    public anonymous?: boolean,
    public donators?: ITreat
  ) {
    this.anonymous = this.anonymous || false;
  }
}
