import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CountryDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/country-description/country-description-detail.component';
import { CountryDescriptionService } from '../../../../../../main/webapp/app/entities/country-description/country-description.service';
import { CountryDescription } from '../../../../../../main/webapp/app/entities/country-description/country-description.model';

describe('Component Tests', () => {

    describe('CountryDescription Management Detail Component', () => {
        let comp: CountryDescriptionDetailComponent;
        let fixture: ComponentFixture<CountryDescriptionDetailComponent>;
        let service: CountryDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [CountryDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CountryDescriptionService,
                    EventManager
                ]
            }).overrideComponent(CountryDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CountryDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CountryDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CountryDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.countryDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
