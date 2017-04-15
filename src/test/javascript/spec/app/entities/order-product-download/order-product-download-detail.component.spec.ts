import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrderProductDownloadDetailComponent } from '../../../../../../main/webapp/app/entities/order-product-download/order-product-download-detail.component';
import { OrderProductDownloadService } from '../../../../../../main/webapp/app/entities/order-product-download/order-product-download.service';
import { OrderProductDownload } from '../../../../../../main/webapp/app/entities/order-product-download/order-product-download.model';

describe('Component Tests', () => {

    describe('OrderProductDownload Management Detail Component', () => {
        let comp: OrderProductDownloadDetailComponent;
        let fixture: ComponentFixture<OrderProductDownloadDetailComponent>;
        let service: OrderProductDownloadService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [OrderProductDownloadDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrderProductDownloadService,
                    EventManager
                ]
            }).overrideComponent(OrderProductDownloadDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderProductDownloadDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderProductDownloadService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrderProductDownload(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.orderProductDownload).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
