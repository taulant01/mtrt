import { IImage } from 'app/shared/model/image.model';
import { IUser } from 'app/core/user/user.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface ITreat {
  id?: number;
  crock?: string;
  title?: string;
  description?: string;
  purchaseLink?: string;
  generatedLink?: string;
  status?: Status;
  treats?: IImage[];
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
    public status?: Status,
    public treats?: IImage[],
    public user?: IUser
  ) {}
}
