import { TestBed } from '@angular/core/testing';

import { CareTakerService } from './care-taker.service';

describe('CareTakerService', () => {
  let service: CareTakerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CareTakerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
