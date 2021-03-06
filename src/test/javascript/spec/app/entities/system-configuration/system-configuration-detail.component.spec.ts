import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { EshopTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SystemConfigurationDetailComponent } from '../../../../../../main/webapp/app/entities/system-configuration/system-configuration-detail.component';
import { SystemConfigurationService } from '../../../../../../main/webapp/app/entities/system-configuration/system-configuration.service';
import { SystemConfiguration } from '../../../../../../main/webapp/app/entities/system-configuration/system-configuration.model';

describe('Component Tests', () => {

    describe('SystemConfiguration Management Detail Component', () => {
        let comp: SystemConfigurationDetailComponent;
        let fixture: ComponentFixture<SystemConfigurationDetailComponent>;
        let service: SystemConfigurationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EshopTestModule],
                declarations: [SystemConfigurationDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SystemConfigurationService,
                    EventManager
                ]
            }).overrideComponent(SystemConfigurationDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SystemConfigurationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SystemConfigurationService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SystemConfiguration(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.systemConfiguration).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
