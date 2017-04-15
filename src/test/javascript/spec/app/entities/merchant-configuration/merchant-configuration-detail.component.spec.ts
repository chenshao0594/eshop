import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MerchantConfigurationDetailComponent } from '../../../../../../main/webapp/app/entities/merchant-configuration/merchant-configuration-detail.component';
import { MerchantConfigurationService } from '../../../../../../main/webapp/app/entities/merchant-configuration/merchant-configuration.service';
import { MerchantConfiguration } from '../../../../../../main/webapp/app/entities/merchant-configuration/merchant-configuration.model';

describe('Component Tests', () => {

    describe('MerchantConfiguration Management Detail Component', () => {
        let comp: MerchantConfigurationDetailComponent;
        let fixture: ComponentFixture<MerchantConfigurationDetailComponent>;
        let service: MerchantConfigurationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [MerchantConfigurationDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MerchantConfigurationService,
                    EventManager
                ]
            }).overrideComponent(MerchantConfigurationDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MerchantConfigurationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MerchantConfigurationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MerchantConfiguration(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.merchantConfiguration).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
