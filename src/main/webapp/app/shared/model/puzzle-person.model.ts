import { IUser } from 'app/core/user/user.model';
import { IPuzzleRental } from 'app/shared/model/puzzle-rental.model';
import { IPuzzleItem } from 'app/shared/model/puzzle-item.model';

export interface IPuzzlePerson {
  id?: number;
  user?: IUser;
  puzzleRentals?: IPuzzleRental[];
  puzzleItems?: IPuzzleItem[];
}

export class PuzzlePerson implements IPuzzlePerson {
  constructor(public id?: number, public user?: IUser, public puzzleRentals?: IPuzzleRental[], public puzzleItems?: IPuzzleItem[]) {}
}
