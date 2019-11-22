import React from 'react';
import "./style.scss";
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import Paper from '@material-ui/core/Paper';
import Money from '@material-ui/icons/Money';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import FormatAmountService from '../services/FormatAmountService'
import Error from "../utils/Error";

declare const tsConfig: any;

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <Link color="inherit" href="https://material-ui.com/">
        Heracles
      </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

// can only use hooks with function components
const useStyles = makeStyles(theme => ({
  '@global': {
    body: {
      backgroundColor: theme.palette.common.white,
    },
  },
  paper: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

interface State {
  errors: Error[];
  submitSuccess: any;
  formattedAmount: string;
  formatAmountError: any;
  form: any;
}

interface Props {
  closeModal?: any;
}

export default class FormatAmountForm extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);

    this.state = {
      errors: new Array<Error>(),
      submitSuccess: undefined,
      formattedAmount: "",
      formatAmountError: undefined,
      form: {
        currency: "",
        value: "",
        precision: "",
        locale: "",
        decimalPlaces: "",
        thousandsSeparator: "",
        decimalSeparator: "",
      },
    };
  }

  public clearErrors() {
    this.setState({
      errors: new Array<Error>(),
    });
  }

  public clear() {
    this.setState({
      form: {
        currency: "",
        value: "",
        precision: "",
        locale: "",
        decimalPlaces: "",
        thousandsSeparator: "",
        decimalSeparator: "",
      },
      submitSuccess: undefined,
      formatAmountError: undefined,
      formattedAmount: "",
    });
    this.clearErrors();
  }

  // public componentDidMount() {
  //
  // }

  public render() {
    const { submitSuccess } = this.state;
    return (
        <Container component="main" maxWidth="xs">
          <CssBaseline />
          <div className="">

            {/*<Avatar className="">*/}
            {/*  <Money />*/}
            {/*</Avatar>*/}

            <Typography component="h1" variant="h5" align="center">
              Format Amount
            </Typography>

            <form
                className=""
                onSubmit={(e) => { this.handleSubmit(e); }}
                noValidate={true}
                id="formatAmountForm"
            >
              {/* currency field*/}
              <Grid item xs={12}>
                {this.fieldErrors("currency").map((error) => {
                  return <span className="error" key={error.error}>{error.error}</span>
                })}
                <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="currency"
                    name="currency"
                    label="Amount currency"
                    type="text"
                    autoComplete=""
                    value={this.state.form.currency}
                    autoFocus
                    onChange={(event) => {
                      this.clearErrors()
                      const form = this.state.form;
                      form.currency = event.target.value
                      this.setState({
                        form,
                      });
                    }}
                />
              </Grid>

              {/* value field*/}
              <Grid item xs={12}>
                {this.fieldErrors("value").map((error) => {
                  return <span className="error" key={error.error}>{error.error}</span>;
                })}
                <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="value"
                    name="value"
                    label="Amount value in minor units"
                    type="number"
                    autoComplete=""
                    value={this.state.form.value}
                    autoFocus
                    onChange={(event) => {
                      this.clearErrors()
                      const form = this.state.form;
                      form.value = event.target.value
                      this.setState({
                        form,
                      });
                    }}
                />
              </Grid>

              {/* precision field*/}
              <Grid item xs={12}>
                {this.fieldErrors("precision").map((error) => {
                  return <span className="error" key={error.error}>{error.error}</span>;
                })}
                <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="precision"
                    name="precision"
                    label="Amount precision"
                    type="number"
                    autoComplete=""
                    autoFocus
                    onChange={(event) => {
                      this.clearErrors()
                      const form = this.state.form;
                      form.precision = event.target.value
                      this.setState({
                        form,
                      });
                    }}
                />
              </Grid>

              <Button
                  type="submit"
                  fullWidth
                  variant="contained"
                  color="primary"
                  className=""
                  disabled={this.haveErrors()}
              >
                Format
              </Button>

              {/*<Grid container>*/}
              {/*  <Grid item xs>*/}
              {/*    <Link href="#" variant="body2">*/}
              {/*      Forgot password?*/}
              {/*    </Link>*/}
              {/*  </Grid>*/}
              {/*  <Grid item>*/}
              {/*    <Link href="#" variant="body2">*/}
              {/*      {"Don't have an account? Sign Up"}*/}
              {/*    </Link>*/}
              {/*  </Grid>*/}
              {/*</Grid>*/}
            </form>

            {submitSuccess && (
                <Paper className="formattedAmountContainer">
                  Formatted Amount: {this.state.formattedAmount}
                </Paper>
            )}
            {submitSuccess === false && !this.haveErrors() && (
                <div className="error">
                  Sorry, an unexpected error has occurred
                </div>
            )}
            {/*{this.haveErrors() && (*/}
            {/*    <div className="error">*/}
            {/*      Sorry, the form is invalid. Please review, adjust and try again*/}
            {/*    </div>*/}
            {/*)}*/}
            {this.state.formatAmountError && (
                <div className="error">
                  {this.state.formatAmountError}
                </div>
            )}
          </div>

          <Box mt={8}>
            <Copyright />
          </Box>
        </Container>
    );
  }

  private haveErrors() {
    return this.state.errors.length > 0;
  }

  private fieldErrors(name: string): Error[] {
    const errors: Error[] =  this.state.errors.filter((element: any, index, array) => {
      return element.key === name;
    });
    return errors;
  }

  private handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    this.setState ({
      formatAmountError: undefined,
      submitSuccess: undefined,
    });

    if (this.validateForm()) {
      FormatAmountService
          .formatAmount(this.state.form)
          .then((result) => {
            this.setState({ submitSuccess: true });
            this.setState( {formattedAmount: result.data.formattedAmount })
            this.props.closeModal();
            this.clear();
          })
          .catch((err) => {
            if (err.response && err.response.status === 403) {
              localStorage.clear();
              window.location.replace(`${tsConfig.REACT_APP_UI_URL}/`);
            } else if (err.response.data && err.response.data.header) {
              this.setState ({
                formatAmountError: err.response.data.header.responseStatus.errorMessage,
              });
            } else {
              this.setState ({
                formatAmountError: err.message,
                submitSuccess: false,
              });
            }
          });
    }
  }

  /**
   * Executes the validation rules for all the fields on the form and sets the error state
   * @returns {boolean} - Whether the form is valid or not
   */
  private validateForm(): boolean {
    const errors = new Array<Error>();
    if (!this.state.form.currency) {
      errors.push(new Error("currency", "Amount currency is required"));
    }

    if (!this.state.form.value) {
      errors.push(new Error("value", "Amount value in minor units is required"));
    }

    if (!this.state.form.precision) {
      errors.push(new Error("precision", "Amount precision is required"));
    }

    this.setState({ errors });

    return errors.length === 0;
  }
}
