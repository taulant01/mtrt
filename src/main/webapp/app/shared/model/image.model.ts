import { ITreat } from 'app/shared/model/treat.model';

export interface IImage {
  id?: number;
  imageContentType?: string;
  image?: any;
  images?: ITreat[];
}

export class Image implements IImage {
  constructor(public id?: number, public imageContentType?: string, public image?: any, public images?: ITreat[]) {}
}
