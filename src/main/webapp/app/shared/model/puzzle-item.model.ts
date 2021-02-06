import { IPuzzleRental } from 'app/shared/model/puzzle-rental.model';
import { IPuzzlePerson } from 'app/shared/model/puzzle-person.model';
import { PuzzleType } from 'app/shared/model/enumerations/puzzle-type.model';

export interface IPuzzleItem {
  id?: number;
  name?: string;
  description?: string;
  type?: PuzzleType;
  pictureContentType?: string;
  picture?: any;
  puzzleRental?: IPuzzleRental;
  puzzlePerson?: IPuzzlePerson;
}

export class PuzzleItem implements IPuzzleItem {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public type?: PuzzleType,
    public pictureContentType?: string,
    public picture?: any,
    public puzzleRental?: IPuzzleRental,
    public puzzlePerson?: IPuzzlePerson
  ) {}
}
