import { Moment } from 'moment';
import { IPuzzleItem } from 'app/shared/model/puzzle-item.model';
import { IPuzzlePerson } from 'app/shared/model/puzzle-person.model';

export interface IPuzzleRental {
  id?: number;
  startDate?: Moment;
  endDate?: Moment;
  isActive?: boolean;
  puzzleItem?: IPuzzleItem;
  puzzlePerson?: IPuzzlePerson;
}

export class PuzzleRental implements IPuzzleRental {
  constructor(
    public id?: number,
    public startDate?: Moment,
    public endDate?: Moment,
    public isActive?: boolean,
    public puzzleItem?: IPuzzleItem,
    public puzzlePerson?: IPuzzlePerson
  ) {
    this.isActive = this.isActive || false;
  }
}
