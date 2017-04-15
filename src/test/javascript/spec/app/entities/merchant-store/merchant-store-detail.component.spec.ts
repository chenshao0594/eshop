import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MerchantStoreDetailComponent } from '../../../../../../main/webapp/app/entities/merchant-store/merchant-store-detail.component';
import { MerchantStoreService } from '../../../../../../main/webapp/app/entities/merchant-store/merchant-store.service';
import { MerchantStore } from '../../../../../../main/webapp/app/entities/merchant-store/merchant-store.model';

describe('Component Tests', () => {

    describe('MerchantStore Management Detail Component', () => {
        let comp: MerchantStoreDetailComponent;
        let fixture: ComponentFixture<MerchantStoreDetailComponent>;
        let service: MerchantStoreService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [MerchantStoreDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MerchantStoreService,
                    EventManager
                ]
            }).overrideComponent(MerchantStoreDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MerchantStoreDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantStoreService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MerchantStore(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.merchantStore).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
