import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DonatorService } from 'app/entities/donator/donator.service';
import { IDonator, Donator } from 'app/shared/model/donator.model';

describe('Service Tests', () => {
  describe('Donator Service', () => {
    let injector: TestBed;
    let service: DonatorService;
    let httpMock: HttpTestingController;
    let elemDefault: IDonator;
    let expectedResult: IDonator | IDonator[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DonatorService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Donator(0, 'AAAAAAA', 'AAAAAAA', currentDate, 0, 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            paymentDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Donator', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            paymentDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Donator()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Donator', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            surname: 'BBBBBB',
            paymentDate: currentDate.format(DATE_FORMAT),
            amount: 1,
            message: 'BBBBBB',
            anonymous: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Donator', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            surname: 'BBBBBB',
            paymentDate: currentDate.format(DATE_FORMAT),
            amount: 1,
            message: 'BBBBBB',
            anonymous: true,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Donator', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
