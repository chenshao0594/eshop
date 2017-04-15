import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TaxRateDetailComponent } from '../../../../../../main/webapp/app/entities/tax-rate/tax-rate-detail.component';
import { TaxRateService } from '../../../../../../main/webapp/app/entities/tax-rate/tax-rate.service';
import { TaxRate } from '../../../../../../main/webapp/app/entities/tax-rate/tax-rate.model';

describe('Component Tests', () => {

    describe('TaxRate Management Detail Component', () => {
        let comp: TaxRateDetailComponent;
        let fixture: ComponentFixture<TaxRateDetailComponent>;
        let service: TaxRateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [TaxRateDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TaxRateService,
                    EventManager
                ]
            }).overrideComponent(TaxRateDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TaxRateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TaxRateService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TaxRate(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.taxRate).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
