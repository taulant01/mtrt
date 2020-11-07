import { IUser } from 'app/core/user/user.model';

export interface ITreat {
  id?: number;
  crock?: string;
  title?: string;
  description?: string;
  purchaseLink?: string;
  generatedLink?: string;
  user?: IUser;
}

export class Treat implements ITreat {
  constructor(
    public id?: number,
    public crock?: string,
    public title?: string,
    public description?: string,
    public purchaseLink?: string,
    public generatedLink?: string,
    public user?: IUser
  ) {}
}
