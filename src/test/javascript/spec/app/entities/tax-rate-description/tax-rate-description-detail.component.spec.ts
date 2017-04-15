import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TaxRateDescriptionDetailComponent } from '../../../../../../main/webapp/app/entities/tax-rate-description/tax-rate-description-detail.component';
import { TaxRateDescriptionService } from '../../../../../../main/webapp/app/entities/tax-rate-description/tax-rate-description.service';
import { TaxRateDescription } from '../../../../../../main/webapp/app/entities/tax-rate-description/tax-rate-description.model';

describe('Component Tests', () => {

    describe('TaxRateDescription Management Detail Component', () => {
        let comp: TaxRateDescriptionDetailComponent;
        let fixture: ComponentFixture<TaxRateDescriptionDetailComponent>;
        let service: TaxRateDescriptionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [TaxRateDescriptionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TaxRateDescriptionService,
                    EventManager
                ]
            }).overrideComponent(TaxRateDescriptionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TaxRateDescriptionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaxRateDescriptionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TaxRateDescription(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.taxRateDescription).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
