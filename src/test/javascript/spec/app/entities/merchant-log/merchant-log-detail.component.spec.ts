import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MerchantLogDetailComponent } from '../../../../../../main/webapp/app/entities/merchant-log/merchant-log-detail.component';
import { MerchantLogService } from '../../../../../../main/webapp/app/entities/merchant-log/merchant-log.service';
import { MerchantLog } from '../../../../../../main/webapp/app/entities/merchant-log/merchant-log.model';

describe('Component Tests', () => {

    describe('MerchantLog Management Detail Component', () => {
        let comp: MerchantLogDetailComponent;
        let fixture: ComponentFixture<MerchantLogDetailComponent>;
        let service: MerchantLogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [MerchantLogDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MerchantLogService,
                    EventManager
                ]
            }).overrideComponent(MerchantLogDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MerchantLogDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantLogService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MerchantLog(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.merchantLog).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
